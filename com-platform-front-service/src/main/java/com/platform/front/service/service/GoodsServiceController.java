package com.platform.front.service.service;


import com.platform.common.modal.PageData;
import com.platform.front.service.biz.TaobaoGoodsBiz;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.modal.GoodsInfo;
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
    TaobaoGoodsBiz taobaoGoodsBiz;

    @PostMapping("/list")
    public PageData<GoodsInfo> findTaobaoGoodsList(@RequestBody FindGoodsListParam param){
        return taobaoGoodsBiz.findGoodsList(param);
    }
}
