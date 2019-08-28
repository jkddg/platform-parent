package com.platform.admin.service.biz;

import com.alibaba.fastjson.JSON;
import com.platform.admin.service.api.JdAPI;
import com.platform.admin.service.client.param.JdGoodsSyncParam;
import com.platform.admin.service.mapper.JdGoodsMapper;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.GoodsCategory;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.es.EsWriteUtil;
import jd.union.open.goods.jingfen.query.response.JFGoodsResp;
import jd.union.open.goods.jingfen.query.response.UnionOpenGoodsJingfenQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdGoodsSyncBiz {

    @Autowired
    JdAPI jdAPI;

    private final static int successCode = 200;


    public ResultInfo<JdGoodsSyncParam> queryAndSaveGoods(JdGoodsSyncParam jdGoodsSyncParam) {
        ResultInfo<JdGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setData(jdGoodsSyncParam);
        UnionOpenGoodsJingfenQueryResponse response = jdAPI.queryJinfenGoods(jdGoodsSyncParam.getEliteId(), jdGoodsSyncParam.getPageSize(), jdGoodsSyncParam.getPageIndex(), jdGoodsSyncParam.getSortName(), jdGoodsSyncParam.getSort());
        if (response != null && response.getCode() == successCode) {
            List<Map<String, Object>> list = new ArrayList<>();
            if (response.getData() != null && response.getData().length > 0) {
                jdGoodsSyncParam.setTotalCount(response.getTotalCount());
                for (int i = 0; i < response.getData().length; i++) {
                    GoodsInfo goodsInfo = JdGoodsMapper.convertGoods(response.getData()[i]);

                    if (!filterGoods(goodsInfo)) {
                        continue;
                    }
                    if (goodsInfo != null) {
                        Map<String, Object> map = new HashMap<>();
                        map = JSON.parseObject(JSON.toJSONString(goodsInfo));
                        list.add(map);
                    }
                }
            }
            if (list != null && list.size() > 0) {
                EsWriteUtil.addList(list, EsConstanst.ES_GOODS_INDEX_NAME, EsConstanst.ES_GOODS_ID_COLUMN_NAME);
            }
            appendCategory(response.getData());
            resultInfo.setSuccess(true);

        } else {
            resultInfo.setSuccess(false);
            if (response != null) {
                resultInfo.setMsg(response.getMessage());
            }

        }
        return resultInfo;
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
        if (goodsInfo.getVolume() == 0) {
            return false;
        }
        /**
         * 月销量大于1万，或券面额大于20
         */
        if (goodsInfo.getVolume() > 10000 || goodsInfo.getCouponAmount() >= 20) {
            return true;
        }
        return false;
    }

    /**
     * 批量保存分类信息
     *
     * @param sourceGoodsList
     */
    private void appendCategory(JFGoodsResp[] sourceGoodsList) {
        if (sourceGoodsList == null || sourceGoodsList.length == 0) {
            return;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (JFGoodsResp data : sourceGoodsList) {
            if (data == null) {
                continue;
            }
            GoodsCategory category = new GoodsCategory();
            if (data.getCategoryInfo() != null) {
                category.setLevelOneCategoryId(data.getCategoryInfo().getCid1());
                category.setLevelOneCategoryName(data.getCategoryInfo().getCid1Name());
                category.setCategoryId(data.getCategoryInfo().getCid2());
                category.setCategoryName(data.getCategoryInfo().getCid2Name());
            } else {
                continue;
            }
            category.setPlatformId(PlatformEnum.JD.value());
            category.setPlatformName(PlatformEnum.JD.displayName());

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
