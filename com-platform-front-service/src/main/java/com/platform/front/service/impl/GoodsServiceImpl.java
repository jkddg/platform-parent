package com.platform.front.service.impl;

import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.front.service.biz.GoodsQueryBiz;
import com.platform.front.service.biz.TaobaoGoodsBiz;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.client.param.TpwdParam;
import com.platform.front.service.iface.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Huangyonghao on 2019/6/18 13:23.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    TaobaoGoodsBiz taobaoGoodsBiz;

    @Autowired
    GoodsQueryBiz goodsQueryBiz;

    @Override
    public PageData<GoodsInfo> findGoodsList(FindGoodsListParam param){
        PageData<GoodsInfo> result=new PageData<>();
        result.setTotalCount(0);
        result = goodsQueryBiz.findGoodsList(param);
        return result;
    }

    @Override
    public String getTpwd(TpwdParam tpwdParam){
        return taobaoGoodsBiz.getTpwd(tpwdParam);
    }
}
