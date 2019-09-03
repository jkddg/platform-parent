package com.platform.spider.spiderUtil;

import com.alibaba.fastjson.JSONObject;
import com.platform.spider.spiderUtil.middle.MiddleHttpRequestRetryHandler;
import com.platform.spider.spiderUtil.setting.SettingDefault;
import com.platform.spider.spiderUtil.setting.Settings;
import com.platform.spider.spiderUtil.thread.ThreadManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class Spider implements SpiderIpl {
    private PoolingHttpClientConnectionManager cm = null;
    private CloseableHttpClient httpClient;
    private HttpContext context;
    private RequestConfig requestConfig;
    private HttpClientBuilder clientBuilder;

    private Settings settings;

    private String name = "";
    private String customSettings = "";

    //统计
    private StatisticsInfo statisticsInfo = null;

    //url列表
    public static ConcurrentLinkedQueue<Map<String, Object>> urlTasks = new ConcurrentLinkedQueue<>();

    //
    private ThreadManager threadManager;

    public Spider() {
        this.statisticsInfo = new StatisticsInfo();
        this.statisticsInfo.setStartTime(System.currentTimeMillis());

        this.name = this.getName();
        if (this.name == null || this.name.length() == 0) {
            log.error("name must be not null or ''");
            System.exit(1);
        }
        this.customSettings = this.getCustomSettings();

        this.cm = new PoolingHttpClientConnectionManager();
        this.cm.setMaxTotal(100);
        this.cm.setDefaultMaxPerRoute(20);

        try {
            this.settings = new Settings(this.customSettings);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            System.exit(-1);
        }

        this.requestConfig = RequestConfig.custom()
                .setSocketTimeout((int) settings.download_timeout * 1000)
                .setConnectionRequestTimeout((int) settings.download_timeout * 1000)
                .setConnectTimeout((int) settings.download_timeout * 1000)
                .build();

        this.clientBuilder = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(this.requestConfig)
                .disableCookieManagement();

        if (settings.retry_enabled) {
            clientBuilder = clientBuilder.setRetryHandler(new MiddleHttpRequestRetryHandler(settings.retry_times, false));
        } else {
            clientBuilder = clientBuilder.setRetryHandler(new MiddleHttpRequestRetryHandler(SettingDefault.RETRY_TIMES, false));
        }

        this.httpClient = clientBuilder.build();

    }

    public void startRequests() {

    }

    private void dealRequests(SpiderRequest request, Spider spider) {
        try {
            request.start(this.clientBuilder, spider, this.requestConfig, statisticsInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 添加url任务
     *
     * @param request
     * @param spider
     * @return void
     * @author mtime
     * @date 2018/7/26 0026
     */
    public void yield(SpiderRequest request, Spider spider) {
        Map<String, Object> map = new HashMap<>();
        map.put("request", request);
        map.put("spider", spider);
        Spider.urlTasks.add(map);

        log.info("add url = " + request.getUrl());

        String name = Thread.currentThread().getName();
        if (!name.contains("main")) {
            return;
        }

        if (this.threadManager == null) {
            this.threadManager = new ThreadManager(this.clientBuilder,
                    this.requestConfig,
                    this.settings,
                    this.statisticsInfo);
            this.threadManager.start();
        }
    }

    /**
     * 处理数据
     *
     * @param object
     * @param spider
     * @return void
     * @author mtime
     * @date 2018/7/26 0026
     */
    public void yield(JSONObject object, Spider spider) {
        try {
            List<String> pipelines = this.settings.getPipelines();

            for (String pipeline : pipelines) {

                Class<?> cl = Class.forName(pipeline);
                Method[] methods = cl.getDeclaredMethods();

                //openItem
                for (Method method : methods) {
                    if (method.getName().equals("openItem")) {
                        method.invoke(cl.newInstance(), spider);
                    }
                }

                //processItem
                for (Method method : methods) {
                    if (method.getName().equals("processItem")) {
                        object = (JSONObject) method.invoke(cl.newInstance(), object, spider);
                    }
                }

                //closeItem
                for (Method method : methods) {
                    if (method.getName().equals("closeItem")) {
                        method.invoke(cl.newInstance(), spider);
                    }
                }

                if (object == null) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private PipeLines pipeLines = null;

    public Settings getSettings() {
        return settings;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public PipeLines getPipeLines() {
        return pipeLines;
    }

    public void setPipeLines(PipeLines pipeLines) {
        this.pipeLines = pipeLines;
    }

    @Override
    public String getName() {
        return null;
    }

}
