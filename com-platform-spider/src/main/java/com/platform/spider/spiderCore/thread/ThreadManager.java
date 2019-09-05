package com.platform.spider.spiderCore.thread;

import com.platform.spider.spiderCore.Spider;
import com.platform.spider.spiderCore.StatisticsInfo;
import com.platform.spider.spiderCore.setting.Settings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ThreadManager extends Thread {
    private boolean isStop = false;
    private HttpClientBuilder clientBuilder;
    private RequestConfig config;
    private Settings settings;
    private StatisticsInfo statisticsInfo;

    public ThreadManager() {
        this.clientBuilder = clientBuilder;
        this.config = config;
        this.settings = settings;
        this.statisticsInfo = statisticsInfo;
    }

    /**
     * 运行队列任务
     *
     * @return void
     * @author mtime
     * @date 2018/7/26 0026
     */
    @Override
    public void run() {
        while (!this.isStop) {
            Spider spider = this.getSpiderTask();
            if (spider != null) {
                log.info(String.format("spider start url = %s", spider.getUrl()));
                try {
                    spider.startRequests();
                } catch (RuntimeException e) {
                    log.error(e.getMessage(), e);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                log.info(String.format("crawl end url = %s", spider.getUrl()));
            }
            try {
                Thread.sleep((long) settings.download_delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private Spider getSpiderTask() {
        try {
            log.info("task size = " + Spider.spiderTasks.size());
            return Spider.spiderTasks.poll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
