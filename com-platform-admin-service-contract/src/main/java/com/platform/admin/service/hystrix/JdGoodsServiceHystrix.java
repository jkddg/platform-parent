package com.platform.admin.service.hystrix;

import com.platform.admin.service.client.param.JdGoodsSyncParam;
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
