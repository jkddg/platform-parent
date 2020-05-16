package com.platform.admin.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author huangyonghao
 */
@EnableZuulProxy
@SpringBootApplication
public class PlatformAdminZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformAdminZuulApplication.class, args);
    }
}
