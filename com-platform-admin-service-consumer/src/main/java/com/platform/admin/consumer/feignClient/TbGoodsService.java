package com.platform.admin.consumer.feignClient;


import com.platform.common.param.admin.TbGoodsSyncParam;
import com.platform.admin.consumer.hystrix.TbGoodsServiceHystrix;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-admin-service", fallback = TbGoodsServiceHystrix.class)
public interface TbGoodsService {

    @PostMapping("/tb/goods/goodsSync")
    ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam);
}
