package com.platform.front.service.config;

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
public class JdConfig {
    @Value("${jd.server.url}")
    private String serverUrl;
    @Value("${jd.appKey}")
    private String appKey;
    @Value("${jd.appSecret}")
    private String appSecret;
    @Value("${jd.accessToken}")
    private String accessToken;
}
