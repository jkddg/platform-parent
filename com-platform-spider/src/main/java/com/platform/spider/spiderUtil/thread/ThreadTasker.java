package com.platform.spider.spiderUtil.thread;

import com.platform.spider.spiderUtil.Spider;
import com.platform.spider.spiderUtil.SpiderRequest;
import com.platform.spider.spiderUtil.StatisticsInfo;
import com.platform.spider.spiderUtil.setting.Settings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class ThreadTasker extends Thread {
    private boolean isStop = false;

    private HttpClientBuilder clientBuilder;
    private RequestConfig config;
    private Settings settings;
    private StatisticsInfo statisticsInfo;

    public ThreadTasker(HttpClientBuilder clientBuilder,
                        RequestConfig config,
                        Settings settings,
                        StatisticsInfo statisticsInfo) {
        this.clientBuilder = clientBuilder;
        this.config = config;
        this.settings = settings;
        this.statisticsInfo = statisticsInfo;
    }

    @Override
    public void run() {

        while (!this.isStop) {
            Map<String, Object> task = this.getMapTask();
            if (task != null) {
                SpiderRequest request = (SpiderRequest) task.get("request");
                Spider spider = (Spider) task.get("spider");

                log.info(String.format("crawl start url = %s", request.getUrl()));
                try {
                    request.start(this.clientBuilder, spider, this.config, this.statisticsInfo);
                } catch (RuntimeException e) {
                    log.error(e.getMessage(), e);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                log.info(String.format("crawl end url = %s", request.getUrl()));
            }
            try {
                Thread.sleep((long) settings.download_delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private Map<String, Object> getMapTask() {
        try {
            log.info("task size = " + Spider.urlTasks.size());
            return Spider.urlTasks.poll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
