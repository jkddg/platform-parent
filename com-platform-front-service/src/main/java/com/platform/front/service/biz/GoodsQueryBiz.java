package com.platform.front.service.biz;

import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.common.util.es.EsResult;
import com.platform.common.util.es.EsSearchUtil;
import com.platform.common.util.es.SortEntity;
import com.platform.front.service.client.param.FindGoodsListParam;
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
        String sort = "tk_rate_des";
        switch (param.getSort()) {
            case 1:
                sort = "tk_rate_des";
                break;
            case 2:
                sort = "total_sales_des";
                break;
            case 3:
                sort = "price_asc";
                break;
            case 4:
                sort = "price_des";
                break;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("title", param.getKeyWord());
        params.put("shortTitle", param.getKeyWord());
        List<SortEntity> sortEntity = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        if (param.getPlatformEnum() != null && param.getPlatformEnum().size() > 0) {
            for (int i = 0; i < param.getPlatformEnum().size(); i++) {
                if (sb.length() > 0) {
                    sb.append(" OR ");
                }
                sb.append("platformId:").append(param.getPlatformEnum().get(i).value());
            }
        }
        EsResult<GoodsInfo> esResult = EsSearchUtil.search(EsConstanst.ES_GOODS_INDEX_NAME, params, sb.toString(), null, sortEntity, param.getPageIndex(), param.getPageSize(), GoodsInfo.class);
        result.setSuccess(true);
        result.setTotalCount(esResult.getTotal());
        result.setData(esResult.getData());
        return result;
    }
}
