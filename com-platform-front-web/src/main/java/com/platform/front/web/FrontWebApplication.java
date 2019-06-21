package com.platform.front.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableHystrix
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.platform")
@SpringBootApplication(scanBasePackages = "com.platform")
public class FrontWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontWebApplication.class, args);
    }

}
