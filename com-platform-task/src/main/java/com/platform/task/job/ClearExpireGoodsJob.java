package com.platform.task.job;

import com.platform.task.biz.ClearExpireGoodsBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Huangyonghao on 2019/8/28 10:54.
 */

@Slf4j
@Component
public class ClearExpireGoodsJob {

    @Autowired
    ClearExpireGoodsBiz clearExpireGoodsBiz;

    @Scheduled(cron = "0 30 * * * ? ")
    public void doTask(){
        clearExpireGoodsBiz.clearExpireGoods();
    }

}
