package com.platform.admin.consumer.hystrix;

import com.platform.common.modal.user.UserInfo;
import com.platform.common.modal.ResultInfo;

/**
 * Created by Huangyonghao on 2019/8/29 14:59.
 */
public class UserServiceHystrix {
    public ResultInfo<UserInfo> getUserByAccout(String userName) {
        ResultInfo<UserInfo> resultInfo=new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("熔断");
        return resultInfo;
    }
}
