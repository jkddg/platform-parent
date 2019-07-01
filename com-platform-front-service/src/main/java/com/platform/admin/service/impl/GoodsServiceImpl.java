package com.platform.admin.service.impl;

import com.platform.admin.service.biz.TaobaoGoodsBiz;
import com.platform.admin.service.client.param.TpwdParam;
import com.platform.common.modal.PageData;
import com.platform.admin.service.client.param.FindGoodsListParam;
import com.platform.admin.service.iface.GoodsService;
import com.platform.common.modal.GoodsInfo;
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
//        result = taobaoGoodsBiz.findCouponList(param);
        return result;
    }

    @Override
    public String getTpwd(TpwdParam tpwdParam){
        return taobaoGoodsBiz.getTpwd(tpwdParam);
    }
}
