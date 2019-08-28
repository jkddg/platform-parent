package com.platform.admin.service.impl;

import com.platform.admin.service.iface.GoodsService;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.es.EsWriteUtil;
import com.platform.common.util.es.RangeQueryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        rangeQueryEntity.setMaxValue(LocalDateTime.now().minusDays(3));
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
}
