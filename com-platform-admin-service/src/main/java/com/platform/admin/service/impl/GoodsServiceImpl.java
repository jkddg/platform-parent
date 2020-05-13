package com.platform.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.platform.admin.service.iface.GoodsService;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.manual.ManualMessage;
import com.platform.common.modal.manual.ManualMessageParam;
import com.platform.common.modal.goods.MyCategory;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.es.EsResult;
import com.platform.common.util.es.EsSearchUtil;
import com.platform.common.util.es.EsWriteUtil;
import com.platform.common.util.es.RangeQueryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/8/28 11:09.
 */
@Component
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Override
    public ResultInfo clearExpireGoods() {
        ResultInfo resultInfo = new ResultInfo();
        List<RangeQueryEntity> rangeQuery = new ArrayList<>();
        RangeQueryEntity rangeQueryEntity = new RangeQueryEntity();
        rangeQueryEntity.setFieldName("updateTime");
        rangeQueryEntity.setMaxValue(LocalDateTime.now().minusDays(1));
        rangeQuery.add(rangeQueryEntity);
        EsWriteUtil.deleteByQuery(EsConstanst.ES_GOODS_INDEX_NAME, null, rangeQuery, null);

        rangeQuery = new ArrayList<>();
        rangeQueryEntity = new RangeQueryEntity();
        rangeQueryEntity.setFieldName("couponEndTime");
        rangeQueryEntity.setMaxValue(LocalDateTime.now());
        rangeQuery.add(rangeQueryEntity);
        EsWriteUtil.deleteByQuery(EsConstanst.ES_GOODS_INDEX_NAME, null, rangeQuery, null);
        resultInfo.setSuccess(true);
        resultInfo.setMsg("完成");
        return resultInfo;

    }


    @Override
    public ResultInfo appendManualMessage(ManualMessageParam msg) {
        ResultInfo resultInfo = new ResultInfo();
        List<Map<String, Object>> docs = new ArrayList<>();
        for (Long category : msg.getMyCategoryId()) {
            ManualMessage message = new ManualMessage();
            BeanUtils.copyProperties(msg, message);
            message.setMyCategoryId(category);
            message.setMyCategoryName(getCategoryName(category));
            Map<String, Object> map = new HashMap<>();
            map = JSON.parseObject(JSON.toJSONString(message));
            docs.add(map);
        }
        try {
            EsWriteUtil.addList(docs, EsConstanst.ES_MANUAL_MESSAGE_INDEX_NAME, EsConstanst.ES_MANUAL_MESSAGE_ID_COLUMN_NAME);
            resultInfo.setSuccess(true);
        } catch (Exception ex) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg(ex.getMessage());
        }

        return resultInfo;
    }

    @Override
    public ResultInfo<List<MyCategory>> getMyCategorys() {

        ResultInfo<List<MyCategory>> resultInfo = new ResultInfo<>();
        EsResult<MyCategory> result = EsSearchUtil.search(EsConstanst.ES_MY_CATEGORY_INDEX_NAME, null, null, null, null, 1, 100, MyCategory.class);
        if (result != null) {
            resultInfo.setData(result.getData());
            resultInfo.setSuccess(true);
        }
        return resultInfo;
    }

    private String getCategoryName(long categoryId) {
        List<MyCategory> categoryList = getMyCategorys().getData();
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
        for (MyCategory myCategory : categoryList) {
            if (categoryId == myCategory.getMyCategoryId()) {
                return myCategory.getMyCategoryName();
            }
        }
        return "";
    }
}
