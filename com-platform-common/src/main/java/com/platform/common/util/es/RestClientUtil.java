package com.platform.common.util.es;




import com.platform.common.config.EsConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.StringUtils;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by kevin.bao on 2019/3/1.
 */
public class RestClientUtil {

    private static final String CLUSTER_NAME = StringUtils.isEmpty(EsConfig.clusterName)?"ngh-cluster":EsConfig.clusterName;

    private static final String IPS = StringUtils.isEmpty(EsConfig.ips)?"117.122.208.140:9200":EsConfig.ips;

    private static Map<String, RestHighLevelClient> highClient = new HashMap<>();

    private static volatile Object lock = new Object();


    /**
     * 获取ES Client
     *
     * @return
     */
    public static RestHighLevelClient highClient() {
        if (highClient.get(CLUSTER_NAME) == null) {
            synchronized (lock) {
                if (highClient.get(CLUSTER_NAME) == null) {
                    initClusters();
                }
            }
        }
        RestHighLevelClient client = highClient.get(CLUSTER_NAME);
        // 如果初始化完以后获得的连接为空那么就是集群名称不正确
        if (client == null) {
            throw new RuntimeException("集群名称 不正确 :" + CLUSTER_NAME);
        }
        return client;
    }

    /**
     * 初始化集群
     *
     * @throws UnknownHostException
     */
    private static void initClusters() {
        //如果集群的名称不是elasticsearch， 就需要设置集群的名称
        String eshost = IPS;
        String cluster = CLUSTER_NAME;
        if (StringUtils.isEmpty(eshost) || StringUtils.isEmpty(cluster)) {
            throw new RuntimeException("elasticearch 连接配置不正确");
        }
        String[] hosts = eshost.split(",");
        RestClient client;
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        try {
            for (int i = 0; i < hosts.length; i++) {
                String[] add = hosts[i].split(":");
                Integer port = StringUtils.isEmpty(add[1]) ? 9200 : Integer.parseInt(add[1]);
//                httpHosts[i] = new HttpHost(InetAddress.getByName(set[0]), port, "http");
                httpHosts[i] = new HttpHost(add[0], port, "http");

            }

            RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);
            RestHighLevelClient highLevelClient = new RestHighLevelClient(restClientBuilder);
            highClient.put(cluster, highLevelClient);
        } catch (Exception e) {
            throw new RuntimeException("elasticearch 连接配置不正确", e);
        }

    }
}
