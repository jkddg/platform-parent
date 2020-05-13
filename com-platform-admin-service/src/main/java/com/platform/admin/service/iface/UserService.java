package com.platform.admin.service.iface;


import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.user.UserInfo;

/**
 * Created by Huangyonghao on 2019/8/29 14:58.
 */

public interface UserService {


    ResultInfo<UserInfo> getUserByAccout(String userName);


}
