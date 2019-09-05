package com.platform.spider.spiderCore;

import com.alibaba.fastjson.JSONObject;
import com.platform.spider.spiderCore.constant.MiddleProxy;
import com.platform.spider.spiderCore.constant.RequestMethod;
import com.platform.spider.spiderCore.constant.UserAgent;
import com.platform.spider.spiderCore.middle.MiddleHttpRequestRetryHandler;
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
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter
@Setter
public abstract class Spider implements SpiderIface {
    protected PoolingHttpClientConnectionManager cm = null;
    protected CloseableHttpClient httpClient;
    protected HttpContext context;
    protected RequestConfig requestConfig;
    protected HttpClientBuilder clientBuilder;

    protected Settings settings;

    protected String name = "";
    protected String customSettings = "";

    //统计
    protected StatisticsInfo statisticsInfo = null;
    protected HttpRequestBase httpBase;
    protected String url;
    protected RequestMethod method;
    protected Map<String, String> headers;
    protected String body;
    protected Map<String, String> cookies;
    protected Map<String, String> meta;
    protected String encoding;
    protected String callback;
    //url列表
    public static ConcurrentLinkedQueue<Spider> spiderTasks = new ConcurrentLinkedQueue<>();
    private Map<String, String> defaultHeaders;


    public Spider(String name, RequestMethod requestMethod, String encoding) {
        this.name = name;
        if (this.name == null || this.name.length() == 0) {
            log.error("name must be not null or ''");
            throw new RuntimeException("name must be not null");
        }

        this.method = requestMethod;

        this.encoding = encoding;
        this.init();
    }

    protected void init() {
        this.defaultHeaders = new HashMap<>();
        this.defaultHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        this.defaultHeaders.put("Accept-Encoding", "gzip, deflate, br");
        this.defaultHeaders.put("Accept-Language", "zh-CN,zh;q=0.9");
        this.defaultHeaders.put("Connection", "keep-alive");
        this.defaultHeaders.put("Upgrade-Insecure-Requests", "1");
        this.defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        this.statisticsInfo = new StatisticsInfo();
        this.statisticsInfo.setStartTime(System.currentTimeMillis());
        this.customSettings = this.getCustomSettings();

        initCM();

        this.settings = new Settings(this.customSettings);

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

    private void initCM() {

        try {
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) {
                    return true;
                }
            }).build();


            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//		SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
//        ctx.init(null, new TrustManager[] { trustManager }, null);
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);


//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1.2" },
//				new String[] { "TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_256_GCM_SHA384" },
//				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainsf).register("https", sslsf).build();
            this.cm = new PoolingHttpClientConnectionManager(registry);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public String getUrl() {
        return url;
    }

    public void startRequests() {

        int count = -1;
        if (!settings.retry_enabled) {
            settings.retry_counts = 0;
        }
        while (count < settings.retry_counts) {
            count += 1;
            try {
                if (StringUtils.isEmpty(this.url)) {
                    throw new RuntimeException("url must is not null!");
                }
                this.url = this.url.trim();
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
                        if (!StringUtils.isEmpty(host) && port > 0) {
                            if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(password)) {
                                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                                credsProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(user, password));
                                clientBuilder.setDefaultCredentialsProvider(credsProvider);

                                HttpHost temp = new HttpHost(host, port);
                                RequestConfig conf = RequestConfig.custom().setProxy(temp).build();
                                this.httpBase.setConfig(conf);
                            } else {
                                HttpHost temp = new HttpHost(host, port);
                                RequestConfig conf = RequestConfig.custom().setProxy(temp).build();
                                this.httpBase.setConfig(conf);
                            }
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

                CloseableHttpResponse response = this.httpClient.execute(this.httpBase);

                statisticsInfo.addStatus("" + response.getStatusLine().getStatusCode());

                if (response.getStatusLine().getStatusCode() == 200 || Arrays.asList(settings.handle_httpstatus_list).contains(
                        "" + response.getStatusLine().getStatusCode())) {
                    try {
                        responseCallback(new SpiderResponse(response, this.meta));
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

    public abstract void responseCallback(SpiderResponse response);

    public abstract JSONObject processItem(JSONObject item);

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

    public Settings getSettings() {
        return settings;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public String getName() {
        return name;
    }

    private String getUserAgent() {
        List<String> middlewares = settings.getMiddlewares();
        for (String name : middlewares) {
            try {
                Class<?> cl = Class.forName(name);
                if (UserAgent.class.isAssignableFrom(cl)) {
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
                if (MiddleProxy.class.isAssignableFrom(cl)) {
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
