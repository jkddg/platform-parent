package com.platform.admin.service.iface;


import com.platform.common.modal.ResultInfo;
import com.platform.common.param.admin.TbGoodsSyncParam;


public interface TbGoodsService {


    ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam);
}
