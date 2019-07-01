package com.platform.admin.service.biz;

import com.platform.common.modal.PageData;
import com.platform.admin.service.client.param.FindGoodsListParam;
import com.platform.common.modal.GoodsInfo;
import org.springframework.stereotype.Component;

@Component
public class JdGoodsQueryBiz {




    public PageData<GoodsInfo> findGoodsList(FindGoodsListParam param) {
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


        return result;
    }
}
