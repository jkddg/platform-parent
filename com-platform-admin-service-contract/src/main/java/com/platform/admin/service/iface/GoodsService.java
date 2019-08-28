package com.platform.admin.service.iface;

import com.platform.admin.service.hystrix.GoodsServiceHystrix;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Huangyonghao on 2019/8/28 11:02.
 */

@FeignClient(value = "com-platform-admin-service", fallback = GoodsServiceHystrix.class)
public interface GoodsService {

    @PostMapping("/goods/clearExpireGoods")
    ResultInfo clearExpireGoods();
}
