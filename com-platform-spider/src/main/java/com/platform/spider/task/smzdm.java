package com.platform.spider.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class smzdm {


    @Scheduled(cron = "0 49 * * * ? ")
    public void doTask(){


    }
}
