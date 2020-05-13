package com.platform.front.service.biz;

import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.common.util.DateUtil;
import com.platform.common.util.es.EsResult;
import com.platform.common.util.es.EsSearchUtil;
import com.platform.common.util.es.RangeQueryEntity;
import com.platform.common.util.es.SortEntity;
import com.platform.front.consumer.client.param.FindGoodsListParam;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/8/26 10:54.
 * 从ES查询所有平台商品信息
 */

@Service
public class GoodsQueryBiz {
    public PageData<GoodsInfo> findGoodsList(FindGoodsListParam param) {
        if (param.getPageIndex() < 1) {
            param.setPageIndex(1);
        }
        PageData<GoodsInfo> result = new PageData<>();
        if (param == null) {
            result.setSuccess(false);
            result.setMsg("请求参数为空");
            return result;
        }

        /**
         * 1、佣金比例
         * 2、券额度比例
         * 3、券面值
         * 4、销量
         * 5、价格
         */
        List<SortEntity> sortEntity = new ArrayList<>();
        SortEntity sort;
        switch (param.getSort()) {
            case 1:
                sort = new SortEntity("commissionRate", SortOrder.DESC);
                break;
            case 2:
                sort = new SortEntity("couponRate", SortOrder.DESC);
                break;
            case 3:
                sort = new SortEntity("couponAmount", SortOrder.DESC);
                break;
            case 4:
                sort = new SortEntity("volume", SortOrder.DESC);
                break;
            case 5:
                sort = new SortEntity("finalPrice", SortOrder.DESC);
                break;
            default:
                sort = new SortEntity("commissionRate", SortOrder.DESC);
                break;
        }
        sortEntity.add(sort);
        Map<String, Object> params = new HashMap<>();
        params.put("title", param.getKeyWord());
        params.put("shortTitle", param.getKeyWord());


        StringBuilder sb = new StringBuilder();
        if (param.getPlatformEnum() != null && param.getPlatformEnum().size() > 0) {
            for (int i = 0; i < param.getPlatformEnum().size(); i++) {
                if (sb.length() > 0) {
                    sb.append(" OR ");
                }
                sb.append("platformId:").append(param.getPlatformEnum().get(i).value());
            }
        }
        List<RangeQueryEntity> rangeQuery = new ArrayList<>();
        RangeQueryEntity rangeQueryEntity = new RangeQueryEntity();
        rangeQueryEntity.setFieldName("updateTime");
        rangeQueryEntity.setMinValue(DateUtil.getDateStartLocalDateTime());
        rangeQuery.add(rangeQueryEntity);
        EsResult<GoodsInfo> esResult = EsSearchUtil.search(EsConstanst.ES_GOODS_INDEX_NAME, params, sb.toString(), rangeQuery, sortEntity, param.getPageIndex(), param.getPageSize(), GoodsInfo.class);
        result.setSuccess(true);
        result.setTotalCount(esResult.getTotal());
        result.setData(esResult.getData());
        return result;
    }
}
