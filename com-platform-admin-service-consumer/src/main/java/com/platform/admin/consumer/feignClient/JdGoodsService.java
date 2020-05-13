package com.platform.admin.consumer.feignClient;


import com.platform.common.param.admin.JdGoodsSyncParam;
import com.platform.admin.consumer.hystrix.JdGoodsServiceHystrix;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-admin-service", fallback = JdGoodsServiceHystrix.class)
public interface JdGoodsService {

    @PostMapping("/jd/goods/goodsSync")
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam);
}
