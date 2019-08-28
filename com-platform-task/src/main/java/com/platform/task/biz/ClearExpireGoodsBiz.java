package com.platform.task.biz;

import com.platform.admin.service.iface.GoodsService;
import com.platform.common.modal.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Huangyonghao on 2019/8/28 10:59.
 */
@Component
public class ClearExpireGoodsBiz {

    @Autowired
    GoodsService goodsService;

    public void clearExpireGoods() {

        ResultInfo resultInfo = goodsService.clearExpireGoods();
    }
}
