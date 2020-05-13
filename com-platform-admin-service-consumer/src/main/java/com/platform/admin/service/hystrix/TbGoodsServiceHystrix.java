package com.platform.admin.service.hystrix;

import com.platform.admin.service.client.param.TbGoodsSyncParam;
import com.platform.common.modal.ResultInfo;

public class TbGoodsServiceHystrix {
    ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam) {
        ResultInfo<TbGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        resultInfo.setData(tbGoodsSyncParam);
        return resultInfo;
    }
}
