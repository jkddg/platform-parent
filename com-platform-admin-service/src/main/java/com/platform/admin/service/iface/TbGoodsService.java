package com.platform.admin.service.iface;

import com.platform.admin.service.client.param.TbGoodsSyncParam;
import com.platform.admin.service.hystrix.TbGoodsServiceHystrix;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-admin-service", fallback = TbGoodsServiceHystrix.class)
public interface TbGoodsService {

    @PostMapping("/tb/goods/goodsSync")
    ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam);
}
