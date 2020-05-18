package com.platform.admin.consumer.feignClient;



import com.platform.common.modal.user.UserInfo;
import com.platform.common.modal.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Huangyonghao
 * @date 2019/8/29 14:58
 */
@FeignClient(value = "com-platform-admin-service", fallback = UserServiceHystrix.class)
public interface UserService {

    @PostMapping("/user/getUserByAccout")
    ResultInfo<UserInfo> getUserByAccout(String userName);


}
class UserServiceHystrix implements UserService{
    @Override
    public ResultInfo<UserInfo> getUserByAccout(String userName) {
        ResultInfo<UserInfo> resultInfo=new ResultInfo<>();
        resultInfo.setSuccess(false);
        resultInfo.setMsg("熔断");
        return resultInfo;
    }

}