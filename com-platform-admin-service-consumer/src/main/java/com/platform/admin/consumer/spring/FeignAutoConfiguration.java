package com.platform.admin.consumer.spring;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author jkddg
 */
@EnableHystrix
@EnableFeignClients
public class FeignAutoConfiguration {
}
