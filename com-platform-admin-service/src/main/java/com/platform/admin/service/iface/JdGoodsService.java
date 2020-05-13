package com.platform.admin.service.iface;


import com.platform.common.modal.ResultInfo;
import com.platform.common.param.admin.JdGoodsSyncParam;


public interface JdGoodsService {

    ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam);
}
