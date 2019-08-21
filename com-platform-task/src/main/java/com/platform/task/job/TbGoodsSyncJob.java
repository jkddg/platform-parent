package com.platform.task.job;

import com.platform.task.biz.TbGoodsBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TbGoodsSyncJob {

    @Autowired
    TbGoodsBiz tbGoodsBiz;

    @Scheduled(cron = "0 35 * * * ?")
    public void doTask(){
        tbGoodsBiz.syncGoods();
    }
}
