package com.platform.front.consumer.feignClient;


import com.platform.common.modal.goods.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.front.consumer.client.param.FindGoodsListParam;
import com.platform.front.consumer.client.param.TpwdParam;
import com.platform.front.consumer.hystrix.GoodsServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Huangyonghao on 2019/6/18 14:12.
 */
@FeignClient(value = "com-platform-front-service",fallback = GoodsServiceHystrix.class)
public interface GoodsService {

    @PostMapping("/goods/list")
    PageData<GoodsInfo> findGoodsList(FindGoodsListParam param);

    @PostMapping("/goods/lists")
    PageData<GoodsInfo> findGoodsLists(FindGoodsListParam param);

    @PostMapping("/goods/getTpwd")
    String getTpwd(TpwdParam tpwdParam);
}

