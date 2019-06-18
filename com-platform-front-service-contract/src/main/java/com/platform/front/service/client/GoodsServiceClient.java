package com.platform.front.service.client;

import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.modal.GoodsInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 14:12.
 */
@FeignClient(value = "com-platform-front-service")
public interface GoodsServiceClient {

    @PostMapping("/goods/taobao/goods/list")
    List<GoodsInfo> findTaobaoGoodsList(FindGoodsListParam param);
}
