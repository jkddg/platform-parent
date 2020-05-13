package com.platform.admin.service.impl;

import com.platform.admin.service.iface.UserService;
import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.user.UserInfo;
import com.platform.common.util.es.EsResult;
import com.platform.common.util.es.EsSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/8/29 15:00.
 */
@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public ResultInfo<UserInfo> getUserByAccout(String userName) {
        ResultInfo<UserInfo> resultInfo = new ResultInfo<>();
        List<String> values = new ArrayList<>();
        values.add(userName);
        EsResult<UserInfo> esResult = EsSearchUtil.search(EsConstanst.ES_ADMIN_USER_INDEX_NAME, "userName", values, UserInfo.class);
        if (esResult != null && esResult.getData() != null && esResult.getData().size() > 0) {
            UserInfo userInfo = esResult.getData().get(0);
            resultInfo.setData(userInfo);
            resultInfo.setSuccess(true);
        }
        return resultInfo;
    }
}
