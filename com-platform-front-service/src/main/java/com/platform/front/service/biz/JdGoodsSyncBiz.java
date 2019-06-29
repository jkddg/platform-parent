package com.platform.front.service.biz;

import com.alibaba.fastjson.JSON;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.ResultInfo;
import com.platform.front.service.api.JdAPI;
import com.platform.front.service.client.param.JdGoodsSyncParam;
import com.platform.front.service.es.EsWriteUtil;
import com.platform.front.service.mapper.JdGoodsMapper;
import com.platform.front.service.modal.GoodsInfo;
import jd.union.open.goods.jingfen.query.response.UnionOpenGoodsJingfenQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                    if (goodsInfo != null) {
                        Map<String, Object> map = new HashMap<>();
                        map = JSON.parseObject(JSON.toJSONString(goodsInfo));
                        list.add(map);
                    }
                }
            }
            EsWriteUtil.addList(list, EsConstanst.JD_GOODS_INDEX_NAME, EsConstanst.JD_GOODS_ID_COLUMN_NAME);
            resultInfo.setSuccess(true);

        } else {
            resultInfo.setSuccess(false);
            if (response != null) {
                resultInfo.setMsg(response.getMessage());
            }

        }
        return resultInfo;
    }
}
