package com.platform.admin.service.service;


import com.platform.common.modal.PageData;
import com.platform.admin.service.client.param.FindGoodsListParam;
import com.platform.admin.service.client.param.TpwdParam;
import com.platform.admin.service.iface.GoodsService;
import com.platform.common.modal.GoodsInfo;
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

    @PostMapping("/list")
    public PageData<GoodsInfo> findTaobaoGoodsList(@RequestBody FindGoodsListParam param){
        return goodsService.findGoodsList(param);
    }
    @PostMapping("/getTpwd")
    public String getTpwd(@RequestBody TpwdParam tpwdParam) {
        return goodsService.getTpwd(tpwdParam);
    }
}
