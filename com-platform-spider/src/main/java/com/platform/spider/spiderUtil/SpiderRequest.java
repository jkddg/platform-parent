package com.platform.spider.spiderUtil;

import com.platform.spider.spiderUtil.middle.MiddleHttpProxyHandler;
import com.platform.spider.spiderUtil.middle.MiddleHttpUserAgentHandler;
import com.platform.spider.spiderUtil.setting.SettingDefault;
import com.platform.spider.spiderUtil.setting.Settings;
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
import org.apache.http.impl.client.HttpClientBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SpiderRequest {
    private HttpRequestBase httpBase;
    private String url;
    private RequestMethod method;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> cookies;
    private Map<String, String> meta;
    private String encoding;
    private String callback;

    //-------------------------------
    private Map<String, String> defaultHeaders;

    public SpiderRequest(String url,
                         RequestMethod method,
                   Map<String, String> headers,
                   String body,
                   Map<String, String> cookies,
                   Map<String, String> meta,
                   String encoding,
                   String callback) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
        this.cookies = cookies;
        this.meta = meta;
        this.encoding = encoding;
        this.callback = callback;

        this.defaultHeaders = new HashMap<>();
        this.defaultHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        this.defaultHeaders.put("Accept-Encoding", "gzip, deflate, br");
        this.defaultHeaders.put("Accept-Language", "zh-CN,zh;q=0.9");
        this.defaultHeaders.put("Connection", "keep-alive");
        this.defaultHeaders.put("Upgrade-Insecure-Requests", "1");
        this.defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
    }

    public void start(HttpClientBuilder clientBuilder,
                      Spider spider,
                      RequestConfig config,
                      StatisticsInfo statisticsInfo) throws RuntimeException {
        Settings settings = spider.getSettings();

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

                this.httpBase.setConfig(config);

                // user-agent
                String userAgent = this.getUserAgent(settings, this, spider);
                if (userAgent != null) {
                    this.httpBase.setHeader("User-Agent", userAgent);
                }

                // proxy
                String[] proxy = this.getProxy(settings, this, spider);
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
                            Class<?> cl = Class.forName(spider.getClass().getName());
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

    public String getUrl() {
        return url;
    }

    private String getUserAgent(Settings settings, SpiderRequest request, Spider spider) {
        List<String> middlewares = settings.getMiddlewares();
        for (String name : middlewares) {
            try {
                Class<?> cl = Class.forName(name);
                if (MiddleHttpUserAgentHandler.class.isAssignableFrom(cl)) {
                    Method[] methods = cl.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals("processRequest")) {
                            return (String) method.invoke(cl.newInstance(), request, spider);
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

    private String[] getProxy(Settings settings, SpiderRequest request, Spider spider) {
        List<String> middlewares = settings.getMiddlewares();
        for (String name : middlewares) {
            try {
                Class<?> cl = Class.forName(name);
                if (MiddleHttpProxyHandler.class.isAssignableFrom(cl)) {
                    Method[] methods = cl.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals("processRequest")) {
                            return (String[]) method.invoke(cl.newInstance(), request, spider);
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
