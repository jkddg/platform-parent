package com.platform.admin.service.biz;

import com.alibaba.fastjson.JSON;
import com.platform.admin.service.api.TaobaoGoodsAPI;
import com.platform.admin.service.mapper.TbGoodsMapper;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.GoodsCategory;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.ResultInfo;
import com.platform.common.param.admin.TbGoodsSyncParam;
import com.platform.common.util.StringUtil;
import com.platform.common.util.es.EsResult;
import com.platform.common.util.es.EsSearchUtil;
import com.platform.common.util.es.EsWriteUtil;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/8/21 15:24.
 */
@Service
public class TbGoodsSyncBiz {

    @Autowired
    TaobaoGoodsAPI taobaoGoodsAPI;

    public ResultInfo<TbGoodsSyncParam> queryAndSaveGoods(TbGoodsSyncParam tbGoodsSyncParam) {
        ResultInfo<TbGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setData(tbGoodsSyncParam);
        TbkDgMaterialOptionalResponse response = taobaoGoodsAPI.tbkMaterialOptional(tbGoodsSyncParam.isHasCoupon(), tbGoodsSyncParam.getCategorys(), tbGoodsSyncParam.getKeyWord(), tbGoodsSyncParam.getPageSize(), tbGoodsSyncParam.getPageIndex(), tbGoodsSyncParam.getSort(), tbGoodsSyncParam.getMaterialId());
        resultInfo.setSuccess(response.isSuccess());
        if (response.isSuccess()) {
            tbGoodsSyncParam.setTotalCount(response.getTotalResults());
            List<Map<String, Object>> list = new ArrayList<>();
            List<String> values = new ArrayList<>();

            for (int i = 0; i < response.getResultList().size(); i++) {

                TbkDgMaterialOptionalResponse.MapData data = response.getResultList().get(i);
                if (StringUtils.isEmpty(data.getCouponId()) || StringUtils.isEmpty(data.getCouponShareUrl())) {
                    // 没有券的商品跳过
                    continue;
                }
                GoodsInfo goodsInfo = TbGoodsMapper.convertGoods(data);
                if (!filterGoods(goodsInfo)) {
                    continue;
                }
                String tpwd = this.getCommendPwd(goodsInfo);
                goodsInfo.setCommandPwd(tpwd);
                goodsInfo.setSourceFlag(0);
                if (goodsInfo != null) {
                    Map<String, Object> map = new HashMap<>();
                    map = JSON.parseObject(JSON.toJSONString(goodsInfo));
                    list.add(map);
                }
            }
            if (list != null && list.size() > 0) {
                EsWriteUtil.addList(list, EsConstanst.ES_GOODS_INDEX_NAME, EsConstanst.ES_GOODS_ID_COLUMN_NAME);
            }
            appendCategory(response.getResultList());
        }
        return resultInfo;
    }

    private String getTPwd(String text, String url) {
        TbkTpwdCreateResponse response = taobaoGoodsAPI.TbkTpwdCreate(text, url);
        if (response.isSuccess()) {
            return response.getData().getModel();
        }
        return null;
    }

    /**
     * 筛选符合条件规则的商品才留下
     *
     * @param goodsInfo
     * @return
     */
    private boolean filterGoods(GoodsInfo goodsInfo) {
        if (goodsInfo == null) {
            return false;
        }

        /**
         * 过滤没有销量的商品
         */
        if (goodsInfo.getVolume() < 100) {
            return false;
        }
        /**
         * 券额比例大于20%，或月销量大于1万，或券面额大于20
         */

        if (goodsInfo.getCouponRate() >= 2000 || goodsInfo.getVolume() > 10000 || goodsInfo.getCouponAmount() >= 20) {
            return true;
        }
        return false;
    }

    private String getCommendPwd(GoodsInfo goodsInfo) {
        String tpwd = null;
        List<String> values = new ArrayList<>();
        values.add(String.valueOf(goodsInfo.getItemId()));
        EsResult<GoodsInfo> result = EsSearchUtil.search(EsConstanst.ES_GOODS_INDEX_NAME, EsConstanst.ES_GOODS_ID_COLUMN_NAME, values, GoodsInfo.class);
        if (result.getTotal() > 0 && result.getData() != null && result.getData().size() > 0) {
            for (GoodsInfo info : result.getData()) {
                if (info.getPlatformId() == goodsInfo.getPlatformId()) {
                    tpwd = info.getCommandPwd();
                    return tpwd;
                }
            }
        }
        tpwd = this.getTPwd(goodsInfo.getShortTitle(), StringUtil.getHttpsUrl(goodsInfo.getCouponShareUrl()));
        return tpwd;
    }

    /**
     * 批量保存分类信息
     *
     * @param sourceGoodsList
     */
    private void appendCategory(List<TbkDgMaterialOptionalResponse.MapData> sourceGoodsList) {
        if (sourceGoodsList == null || sourceGoodsList.size() == 0) {
            return;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (TbkDgMaterialOptionalResponse.MapData data : sourceGoodsList) {
            if (data == null) {
                continue;
            }
            GoodsCategory category = new GoodsCategory();
            category.setCategoryId(data.getCategoryId());
            category.setCategoryName(data.getCategoryName());
            category.setLevelOneCategoryId(data.getLevelOneCategoryId());
            category.setLevelOneCategoryName(data.getLevelOneCategoryName());
            if (data.getUserType() == 1) {
                category.setPlatformId(PlatformEnum.TMALL.value());
                category.setPlatformName(PlatformEnum.TMALL.displayName());
            } else {
                category.setPlatformId(PlatformEnum.TAOBAO.value());
                category.setPlatformName(PlatformEnum.TAOBAO.displayName());
            }
            category.setUpdateTime(LocalDateTime.now());
            category.setId(category.getPlatformId() + "_" + category.getCategoryId());
            if (category != null) {
                Map<String, Object> map = new HashMap<>();
                map = JSON.parseObject(JSON.toJSONString(category));
                list.add(map);
            }
        }
        if (list != null && list.size() > 0) {
            EsWriteUtil.addList(list, EsConstanst.ES_GOODS_CATEGORY_INDEX_NAME, EsConstanst.ES_GOODS_CATEGORY_ID_COLUMN_NAME);
        }
    }

}
