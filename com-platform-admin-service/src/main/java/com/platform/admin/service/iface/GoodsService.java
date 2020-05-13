package com.platform.admin.service.iface;


import com.platform.common.modal.ManualMessageParam;
import com.platform.common.modal.MyCategory;
import com.platform.common.modal.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/8/28 11:02.
 */


public interface GoodsService {


    ResultInfo clearExpireGoods();



    ResultInfo appendManualMessage(ManualMessageParam msg);


    ResultInfo<List<MyCategory>> getMyCategorys();
}
