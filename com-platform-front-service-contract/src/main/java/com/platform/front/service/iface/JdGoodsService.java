package com.platform.front.service.iface;

import com.platform.common.modal.ResultInfo;
import com.platform.front.service.client.param.JdGoodsSyncParam;
import com.platform.front.service.hystrix.GoodsServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "com-platform-front-service",fallback = GoodsServiceHystrix.class)
public interface JdGoodsService {

    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam);
}
