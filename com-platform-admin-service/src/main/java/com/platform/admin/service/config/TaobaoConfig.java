package com.platform.admin.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Huangyonghao on 2019/6/18 17:52.
 */
@Getter
@Setter
@Component
public class TaobaoConfig {
    @Value("${taobao.tbk-url}")
    private String tbkUrl;
    @Value("${taobao.tbk-appkey}")
    private String tbkAppkey;
    @Value("${taobao.tbk-secret}")
    private String tbkSecret;
    @Value("${taobao.tbk-pid}")
    private String tbkPid;
    @Value("${taobao.tkb-adzoneId}")
    private long tkbAdzoneId;
}
