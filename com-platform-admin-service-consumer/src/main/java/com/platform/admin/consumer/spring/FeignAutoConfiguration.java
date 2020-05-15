package com.platform.admin.consumer.spring;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.platform")
@EnableHystrix
@EnableAutoConfiguration
public class FeignAutoConfiguration {
}
