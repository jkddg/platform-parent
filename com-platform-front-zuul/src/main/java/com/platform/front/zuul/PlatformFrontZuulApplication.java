package com.platform.front.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class PlatformFrontZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformFrontZuulApplication.class,args);
    }
}
