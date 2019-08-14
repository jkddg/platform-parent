package com.platform.admin.service.iface;

import com.platform.admin.service.hystrix.JdGoodsServiceHystrix;
import com.platform.common.modal.ResultInfo;
import com.platform.admin.service.client.param.JdGoodsSyncParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-admin-service",fallback = JdGoodsServiceHystrix.class)
public interface JdGoodsService {

    @PostMapping("/jd/goods/goodsSync")
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam);
}
