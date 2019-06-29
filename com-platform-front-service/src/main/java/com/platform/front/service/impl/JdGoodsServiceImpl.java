package com.platform.front.service.impl;

import com.platform.common.modal.ResultInfo;
import com.platform.front.service.biz.JdGoodsSyncBiz;
import com.platform.front.service.client.param.JdGoodsSyncParam;
import com.platform.front.service.iface.JdGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

public class JdGoodsServiceImpl implements JdGoodsService {


    @Autowired
    JdGoodsSyncBiz jdGoodsSyncBiz;

    @Override
    public ResultInfo<JdGoodsSyncParam> syncGoods(JdGoodsSyncParam jdGoodsSyncParam) {
        ResultInfo<JdGoodsSyncParam> resultInfo = jdGoodsSyncBiz.queryAndSaveGoods(jdGoodsSyncParam);
        return resultInfo;
    }
}
