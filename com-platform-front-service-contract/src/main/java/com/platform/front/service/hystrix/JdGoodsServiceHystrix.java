package com.platform.front.service.hystrix;

import com.platform.common.modal.ResultInfo;
import com.platform.front.service.client.param.JdGoodsSyncParam;

public class JdGoodsServiceHystrix {
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam) {
        ResultInfo<JdGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        resultInfo.setData(jdGoodsSyncParam);
        return resultInfo;
    }
}
