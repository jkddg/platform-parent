package com.platform.front.service.service;


import com.platform.common.modal.ResultInfo;
import com.platform.front.service.client.param.JdGoodsSyncParam;
import com.platform.front.service.iface.JdGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jd/goods")
public class JdGoodsServiceController {

    @Autowired
    JdGoodsService jdGoodsService;
    @RequestMapping("goodsSync")
    ResultInfo<JdGoodsSyncParam> syncGoods(@RequestBody JdGoodsSyncParam jdGoodsSyncParam){
        return jdGoodsService.syncGoods(jdGoodsSyncParam);
    }
}
