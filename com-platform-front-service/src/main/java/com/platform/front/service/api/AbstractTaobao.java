package com.platform.front.service.api;

import com.platform.front.service.config.TaobaoConfig;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Huangyonghao on 2019/6/18 14:50.
 */
@Component
public class AbstractTaobao {

    @Autowired
    protected TaobaoConfig taobaoConfig;

    protected TaobaoClient getTaobaoClient(){

        TaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), taobaoConfig.getTbkAppkey(), taobaoConfig.getTbkSecret());
        return client;

    }
}
