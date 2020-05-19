package com.platform.zipkin.server;


import org.springframework.boot.SpringApplication;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
public class ZipkinApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
