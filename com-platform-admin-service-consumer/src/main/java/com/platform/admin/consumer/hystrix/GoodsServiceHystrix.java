package com.platform.admin.consumer.hystrix;

import com.platform.common.modal.manual.ManualMessageParam;
import com.platform.common.modal.goods.MyCategory;
import com.platform.common.modal.ResultInfo;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/8/28 11:02.
 */
public class GoodsServiceHystrix {

    ResultInfo clearExpireGoods() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        return resultInfo;
    }
    ResultInfo appendManualMessage(ManualMessageParam msg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        return resultInfo;
    }

    ResultInfo<List<MyCategory>> getMyCategorys(){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("超时熔断");
        return resultInfo;
    }
}
