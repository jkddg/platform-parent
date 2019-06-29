package com.platform.front.service.config;

import org.springframework.beans.factory.annotation.Value;

public class EsConfig {
    public static String clusterName;
    public static String ips;

    @Value("${app.conf.es-config.clusterName}")
    public void setClusterName(String clusterName1) {
        clusterName = clusterName1;
    }

    @Value("${app.conf.es-config.ips}")
    public void setIps(String ips1) {
        ips = ips1;
    }
}
