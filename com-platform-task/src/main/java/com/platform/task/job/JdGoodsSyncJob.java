package com.platform.task.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JdGoodsSyncJob {


    @Scheduled(cron = "0 15 * * * ?")
    public void doTask(){

    }
}
