package com.platform.admin.service.hystrix;

import com.platform.common.modal.ResultInfo;

/**
 * Created by Huangyonghao on 2019/8/28 11:02.
 */
public class GoodsServiceHystrix {

    ResultInfo clearExpireGoods() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        return resultInfo;
    }
}
