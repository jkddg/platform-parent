package com.platform.admin.consumer.feignClient;


import com.platform.admin.consumer.hystrix.UserServiceHystrix;
import com.platform.common.modal.user.UserInfo;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Huangyonghao on 2019/8/29 14:58.
 */
@FeignClient(value = "com-platform-admin-service", fallback = UserServiceHystrix.class)
public interface UserService {

    @PostMapping("/user/getUserByAccout")
    ResultInfo<UserInfo> getUserByAccout(String userName);


}
