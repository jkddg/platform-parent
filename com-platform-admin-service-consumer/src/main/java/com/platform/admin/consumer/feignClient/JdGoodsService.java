package com.platform.admin.consumer.feignClient;


import com.platform.common.param.admin.JdGoodsSyncParam;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "com-platform-admin-service", fallback = JdGoodsServiceHystrix.class)
public interface JdGoodsService {

    @PostMapping("/jd/goods/goodsSync")
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam);
}
class JdGoodsServiceHystrix {
    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam) {
        ResultInfo<JdGoodsSyncParam> resultInfo = new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        resultInfo.setData(jdGoodsSyncParam);
        return resultInfo;
    }
}