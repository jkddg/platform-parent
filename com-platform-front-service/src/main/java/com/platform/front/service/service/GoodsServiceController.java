package com.platform.front.service.service;




import com.platform.common.modal.goods.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.front.consumer.client.param.FindGoodsListParam;
import com.platform.front.consumer.client.param.TpwdParam;
import com.platform.front.service.iface.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Huangyonghao on 2019/6/18 16:20.
 */
@RestController
@RequestMapping("/goods")
public class GoodsServiceController {

    @Autowired
    GoodsService goodsService;

    @PostMapping("/lists")
    public PageData<GoodsInfo> findGoodsLists(@RequestBody FindGoodsListParam param) {
        return goodsService.findGoodsList(param);
    }

    @PostMapping("/getTpwd")
    public String getTpwd(@RequestBody TpwdParam tpwdParam) {
        return goodsService.getTpwd(tpwdParam);
    }

    @PostMapping("/list")
    public PageData<GoodsInfo> findGoodsList(@RequestBody FindGoodsListParam param) {
        return goodsService.findGoodsList(param);
    }
}
