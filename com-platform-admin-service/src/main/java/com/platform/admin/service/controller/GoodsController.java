package com.platform.admin.service.controller;

import com.platform.admin.service.iface.GoodsService;
import com.platform.common.modal.ManualMessageParam;
import com.platform.common.modal.MyCategory;
import com.platform.common.modal.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/8/28 11:01.
 */

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/clearExpireGoods")
    public ResultInfo clearExpireGoods() {
        return goodsService.clearExpireGoods();
    }

    @RequestMapping("/appendManualMessage")
    public ResultInfo appendManualMessage(@RequestBody ManualMessageParam msg) {
        return goodsService.appendManualMessage(msg);
    }

    @RequestMapping("/getMyCategorys")
    public ResultInfo<List<MyCategory>> getMyCategorys() {
        return goodsService.getMyCategorys();
    }
}
