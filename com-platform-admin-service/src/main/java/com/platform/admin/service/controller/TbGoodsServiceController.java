package com.platform.admin.service.controller;


import com.platform.admin.service.client.param.TbGoodsSyncParam;
import com.platform.admin.service.iface.TbGoodsService;
import com.platform.common.modal.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tb/goods")
public class TbGoodsServiceController {

    @Autowired
    TbGoodsService tbGoodsService;
    @RequestMapping("/goodsSync")
    ResultInfo<TbGoodsSyncParam> syncGoods(@RequestBody TbGoodsSyncParam tbGoodsSyncParam){
        return tbGoodsService.syncGoods(tbGoodsSyncParam);
    }
}
