package com.platform.admin.service.iface;

import com.platform.admin.service.client.param.TpwdParam;
import com.platform.admin.service.hystrix.GoodsServiceHystrix;
import com.platform.common.modal.PageData;
import com.platform.admin.service.client.param.FindGoodsListParam;
import com.platform.common.modal.GoodsInfo;
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

