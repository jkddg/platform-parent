package com.platform.admin.consumer.hystrix;

import com.platform.common.param.admin.JdGoodsSyncParam;
import com.platform.common.modal.ResultInfo;

public class JdGoodsServiceHystrix {
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam) {
        ResultInfo<JdGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        resultInfo.setData(jdGoodsSyncParam);
        return resultInfo;
    }
}
