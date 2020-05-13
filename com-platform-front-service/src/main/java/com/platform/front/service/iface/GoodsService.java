package com.platform.front.service.iface;


import com.platform.common.modal.PageData;

import com.platform.common.modal.goods.GoodsInfo;
import com.platform.front.consumer.client.param.FindGoodsListParam;
import com.platform.front.consumer.client.param.TpwdParam;

/**
 * Created by Huangyonghao on 2019/6/18 14:12.
 */

public interface GoodsService {


    PageData<GoodsInfo> findGoodsList(FindGoodsListParam param);


    PageData<GoodsInfo> findGoodsLists(FindGoodsListParam param);


    String getTpwd(TpwdParam tpwdParam);
}

