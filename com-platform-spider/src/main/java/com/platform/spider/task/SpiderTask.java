package com.platform.spider.task;

import com.platform.spider.spiderCore.thread.ThreadManager;
import com.platform.spider.spiders.SpiderSmzdmTmall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Huangyonghao on 2019/9/4 16:06.
 */
@Slf4j
@Component
public class SpiderTask {

    @Autowired
    ThreadManager threadManager;

    @Scheduled(cron = "0 31 * * * ? ")
    public void doTask() {
        SpiderSmzdmTmall spiderSmzdmTmall = new SpiderSmzdmTmall();
        threadManager.start();
    }
}
