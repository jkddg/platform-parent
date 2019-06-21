package com.platform.front.service.impl;

import com.platform.common.modal.PageData;
import com.platform.front.service.biz.TaobaoGoodsBiz;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.iface.GoodsService;
import com.platform.front.service.modal.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Huangyonghao on 2019/6/18 13:23.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    TaobaoGoodsBiz taobaoGoodsBiz;

    @Override
    public PageData<GoodsInfo> findGoodsList(FindGoodsListParam param){
        PageData<GoodsInfo> result=new PageData<>();
        result.setTotalCount(0);
        result = taobaoGoodsBiz.findGoodsList(param);
        return result;
    }
}
