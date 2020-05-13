package com.platform.admin.service.impl;

import com.platform.admin.service.biz.TbGoodsSyncBiz;
import com.platform.admin.service.iface.TbGoodsService;
import com.platform.common.modal.ResultInfo;
import com.platform.common.param.admin.TbGoodsSyncParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TbGoodsServiceImpl implements TbGoodsService {


    @Autowired
    TbGoodsSyncBiz tbGoodsSyncBiz;

    @Override
    public ResultInfo<TbGoodsSyncParam> syncGoods(TbGoodsSyncParam tbGoodsSyncParam) {
        ResultInfo<TbGoodsSyncParam> resultInfo = tbGoodsSyncBiz.queryAndSaveGoods(tbGoodsSyncParam);
        return resultInfo;
    }
}
