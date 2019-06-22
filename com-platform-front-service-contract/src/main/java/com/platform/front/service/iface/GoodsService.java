package com.platform.front.service.iface;

import com.platform.common.modal.PageData;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.client.param.TpwdParam;
import com.platform.front.service.hystrix.GoodsServiceHystrix;
import com.platform.front.service.modal.GoodsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Huangyonghao on 2019/6/18 14:12.
 */
@FeignClient(value = "com-platform-front-service",fallback = GoodsServiceHystrix.class)
public interface GoodsService {

    @PostMapping("/goods/list")
    PageData<GoodsInfo> findGoodsList(FindGoodsListParam param);


    @PostMapping("/goods/getTpwd")
    String getTpwd(TpwdParam tpwdParam);
}

