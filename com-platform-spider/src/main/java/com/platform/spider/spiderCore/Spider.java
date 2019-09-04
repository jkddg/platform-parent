package com.platform.spider.spiderCore;

import com.alibaba.fastjson.JSONObject;
import com.platform.spider.spiderCore.constant.RequestMethod;
import com.platform.spider.spiderCore.middle.MiddleHttpRequestRetryHandler;
import com.platform.spider.spiderCore.middle.MiddleUserAgent;
import com.platform.spider.spiderCore.setting.SettingDefault;
import com.platform.spider.spiderCore.setting.Settings;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter
@Setter
public class Spider implements SpiderIface {
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
    private HttpRequestBase httpBase;
    private String url;
    private RequestMethod method;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> cookies;
    private Map<String, String> meta;
    private String encoding;
    private String callback;
    //url列表
    public static ConcurrentLinkedQueue<Spider> spiderTasks = new ConcurrentLinkedQueue<>();
    private Map<String, String> defaultHeaders;

    public Spider() {


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

    private void init() {
        this.defaultHeaders = new HashMap<>();
        this.defaultHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        this.defaultHeaders.put("Accept-Encoding", "gzip, deflate, br");
        this.defaultHeaders.put("Accept-Language", "zh-CN,zh;q=0.9");
        this.defaultHeaders.put("Connection", "keep-alive");
        this.defaultHeaders.put("Upgrade-Insecure-Requests", "1");
        this.defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        this.statisticsInfo = new StatisticsInfo();
        this.statisticsInfo.setStartTime(System.currentTimeMillis());

    }

    public String getUrl() {
        return url;
    }

    public void startRequests() {
        int count = -1;
        while (count < settings.retry_counts) {
            count += 1;
            try {
                if (this.url == null) {
                    throw new RuntimeException("url must is not null!");
                }
                this.url = this.url.trim();
                if (this.url.length() == 0) {
                    throw new RuntimeException("url must is not null!");
                }

                if (this.method == RequestMethod.GET) {
                    this.httpBase = new HttpGet(this.url);
                } else {
                    this.httpBase = new HttpPost(this.url);
                }

                if (this.headers != null) {
                    for (String key : this.headers.keySet()) {
                        this.httpBase.setHeader(key, this.headers.get(key));
                    }
                } else {
                    for (String key : this.defaultHeaders.keySet()) {
                        this.httpBase.setHeader(key, this.headers.get(key));
                    }
                }

                this.httpBase.setConfig(this.requestConfig);

                // user-agent
                String userAgent = this.getUserAgent();
                if (userAgent != null) {
                    this.httpBase.setHeader("User-Agent", userAgent);
                }

                // proxy
                String[] proxy = this.getProxy();
                if (proxy != null && proxy.length == 4) {
                    try {
                        String user = proxy[0];
                        String password = proxy[1];
                        String host = proxy[2];
                        int port = Integer.parseInt(proxy[3]);
                        if (host != null && host.length() != 0) {
                            if ((user != null && user.length() != 0)
                                    && (password != null && password.length() != 0)) {
                                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                                credsProvider.setCredentials(
                                        new AuthScope(host, port),
                                        new UsernamePasswordCredentials(user, password));
                                clientBuilder.setDefaultCredentialsProvider(credsProvider);

                                HttpHost temp = new HttpHost(host, port);
                                RequestConfig conf = RequestConfig.custom()
                                        .setProxy(temp)
                                        .build();
                                this.httpBase.setConfig(conf);
                            } else {
                                HttpHost temp = new HttpHost(host, port);
                                RequestConfig conf = RequestConfig.custom()
                                        .setProxy(temp)
                                        .build();
                                this.httpBase.setConfig(conf);
                            }
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

                CloseableHttpResponse response = clientBuilder.build().execute(this.httpBase);

                statisticsInfo.addStatus("" + response.getStatusLine().getStatusCode());

                if (response.getStatusLine().getStatusCode() == 200 || Arrays.asList(settings.handle_httpstatus_list).contains(
                        "" + response.getStatusLine().getStatusCode())) {
                    try {
                        if (this.callback != null) {
                            Class<?> cl = Class.forName(this.getClass().getName());
                            Method[] methods = cl.getDeclaredMethods();
                            for (Method method : methods) {
                                if (this.callback.equals(method.getName())) {
                                    method.invoke(cl.newInstance(), new SpiderResponse(response, this.meta));
                                    return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
                log.warn(String.format("retry count = %d,url = %s,statusCode = %d", count, url, response.getStatusLine().getStatusCode()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 添加url任务
     *
     * @param spider
     * @return void
     * @author mtime
     * @date 2018/7/26 0026
     */
    public void yield(Spider spider) {
        Spider.spiderTasks.add(spider);
        log.info("add url = " + spider.getUrl());
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

    private String getUserAgent() {
        List<String> middlewares = settings.getMiddlewares();
        for (String name : middlewares) {
            try {
                Class<?> cl = Class.forName(name);
                if (MiddleUserAgent.class.isAssignableFrom(cl)) {
                    Method[] methods = cl.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals("processRequest")) {
                            return (String) method.invoke(cl.newInstance(), this);
                        }
                    }
                    return SettingDefault.USER_AGENT;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private String[] getProxy() {
        List<String> middlewares = settings.getMiddlewares();
        for (String name : middlewares) {
            try {
                Class<?> cl = Class.forName(name);
                if (MiddleHttpProxyHandler.class.isAssignableFrom(cl)) {
                    Method[] methods = cl.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals("processRequest")) {
                            return (String[]) method.invoke(cl.newInstance(), this);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }
}
