package com.platform.admin.service.service;

import com.platform.admin.service.iface.UserService;
import com.platform.admin.service.modal.user.UserInfo;
import com.platform.common.modal.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Huangyonghao on 2019/8/29 15:03.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUserByAccout")
    public ResultInfo<UserInfo> getUserByAccout(@RequestBody String userName) {
        return userService.getUserByAccout(userName);
    }
}
