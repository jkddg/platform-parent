package com.platform.front.service.iface;

import com.platform.common.modal.ResultInfo;
import com.platform.front.service.client.param.JdGoodsSyncParam;
import com.platform.front.service.hystrix.GoodsServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-front-service",fallback = GoodsServiceHystrix.class)
public interface JdGoodsService {

    @PostMapping("/jd/goods/goodsSync")
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam);
}
