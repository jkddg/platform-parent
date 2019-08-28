package com.platform.admin.service.service;

import com.platform.admin.service.iface.GoodsService;
import com.platform.common.modal.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
