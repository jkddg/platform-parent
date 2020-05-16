package com.platform.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author huangyonghao
 */
@EnableTurbine
@SpringBootApplication
public class PlatformTurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformTurbineApplication.class, args);
    }
}
