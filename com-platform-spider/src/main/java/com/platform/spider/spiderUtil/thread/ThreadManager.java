package com.platform.spider.spiderUtil.thread;

import com.platform.spider.spiderUtil.Spider;
import com.platform.spider.spiderUtil.StatisticsInfo;
import com.platform.spider.spiderUtil.setting.Settings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadManager extends Thread {
    private HttpClientBuilder clientBuilder;
    private RequestConfig config;
    private Settings settings;
    private StatisticsInfo statisticsInfo;

    private List<ThreadTasker> list;

    public ThreadManager(HttpClientBuilder clientBuilder,
                         RequestConfig config,
                         Settings settings,
                         StatisticsInfo statisticsInfo) {
        this.clientBuilder = clientBuilder;
        this.config = config;
        this.settings = settings;
        this.statisticsInfo = statisticsInfo;
    }

    /**
     * 运行任务
     *
     * @return void
     * @author mtime
     * @date 2018/7/26 0026
     */
    @Override
    public void run() {
        this.list = new ArrayList<>();
        for (int i = 0; i < this.settings.concurrent_requests; i++) {
            this.list.add(new ThreadTasker(this.clientBuilder,
                    this.config, this.settings, this.statisticsInfo));
        }

        for (Thread thread : this.list) {
            thread.start();
        }

        while (true) {
            if (Spider.urlTasks.size() == 0) {
                try {
                    Thread.sleep(10 * 3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Spider.urlTasks.size() == 0) {
                    break;
                }
            }
        }

        for (ThreadTasker thread : this.list) {
            thread.setStop(true);
        }

        for (ThreadTasker thread : this.list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //over
        log.info(this.statisticsInfo.getStatistics());
    }
}
