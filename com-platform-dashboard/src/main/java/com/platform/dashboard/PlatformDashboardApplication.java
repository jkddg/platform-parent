package com.platform.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author huangyonghao
 */
@EnableHystrixDashboard
@SpringBootApplication
public class PlatformDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformDashboardApplication.class, args);
    }
}
