package com.platform.front.service.impl;

import com.platform.front.service.biz.TaobaoGoodsBiz;
import com.platform.front.service.iface.TaobaoGoodsService;
import com.platform.front.service.modal.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 13:23.
 */
@Service
public class TaobaoGoodsServiceImpl implements TaobaoGoodsService {

    @Autowired
    TaobaoGoodsBiz taobaoGoodsBiz;

    @Override
    public List<GoodsInfo> findGoodsList(){
        return null;
    }
}
