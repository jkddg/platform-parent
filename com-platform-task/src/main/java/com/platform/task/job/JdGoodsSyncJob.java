package com.platform.task.job;

import com.platform.task.biz.JdGoodsBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JdGoodsSyncJob {

    @Autowired
    JdGoodsBiz jdGoodsBiz;

    @Scheduled(cron = "0 6 * * * ?")
    public void doTask(){
        jdGoodsBiz.syncGoods();
    }
}
