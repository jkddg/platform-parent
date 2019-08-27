package com.platform.admin.service.biz;

import com.alibaba.fastjson.JSON;
import com.platform.admin.service.api.TaobaoGoodsAPI;
import com.platform.admin.service.client.param.TbGoodsSyncParam;
import com.platform.admin.service.mapper.TbGoodsMapper;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.StringUtil;
import com.platform.common.util.es.EsWriteUtil;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
                goodsInfo.setCommandPwd(this.getTPwd(goodsInfo.getShortTitle(), StringUtil.getHttpsUrl(goodsInfo.getCouponShareUrl())));
                if (goodsInfo != null) {
                    Map<String, Object> map = new HashMap<>();
                    map = JSON.parseObject(JSON.toJSONString(goodsInfo));
                    list.add(map);
                }
            }
            if (list != null && list.size() > 0) {
                EsWriteUtil.addList(list, EsConstanst.ES_GOODS_INDEX_NAME, EsConstanst.ES_GOODS_ID_COLUMN_NAME);
            }

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


}
