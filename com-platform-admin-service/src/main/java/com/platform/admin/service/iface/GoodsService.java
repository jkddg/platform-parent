package com.platform.admin.service.iface;


import com.platform.common.modal.manual.ManualMessageParam;
import com.platform.common.modal.goods.MyCategory;
import com.platform.common.modal.ResultInfo;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/8/28 11:02.
 */


public interface GoodsService {


    ResultInfo clearExpireGoods();



    ResultInfo appendManualMessage(ManualMessageParam msg);


    ResultInfo<List<MyCategory>> getMyCategorys();
}
