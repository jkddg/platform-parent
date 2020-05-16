package com.platform.admin.consumer.feignClient;


import com.platform.common.param.admin.TbGoodsSyncParam;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-admin-service", fallback = TbGoodsServiceHystrix.class)
public interface TbGoodsService {

    @PostMapping("/tb/goods/goodsSync")
    ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam);
}
class TbGoodsServiceHystrix {
    ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam) {
        ResultInfo<TbGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        resultInfo.setData(tbGoodsSyncParam);
        return resultInfo;
    }
}