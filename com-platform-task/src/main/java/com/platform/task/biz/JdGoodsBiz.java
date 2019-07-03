package com.platform.task.biz;

import com.alibaba.fastjson.JSON;
import com.platform.admin.service.client.param.JdGoodsSyncParam;
import com.platform.admin.service.iface.JdGoodsService;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JdGoodsBiz {

    @Autowired
    JdGoodsService jdGoodsService;


    @Autowired
    ThreadPoolUtil threadPoolUtil;


    public void syncGoods() {

        List<Integer> eliteIds = new ArrayList() {
            {
                add(1);
                add(2);
            }
        };
        for (Integer eliteId : eliteIds) {
            CountDownLatch countDownLatch;
            JdGoodsSyncParam jdGoodsSyncParam = new JdGoodsSyncParam();
            jdGoodsSyncParam.setEliteId(eliteId);
            jdGoodsSyncParam.setPageIndex(1);
            jdGoodsSyncParam.setPageSize(40);
            ResultInfo<JdGoodsSyncParam> response = jdGoodsService.syncGoods(jdGoodsSyncParam);
            if (response.isSuccess()) {
                jdGoodsSyncParam = response.getData();
                countDownLatch = new CountDownLatch((int) jdGoodsSyncParam.getPageCount() - 1);
                for (int i = 2; i <= jdGoodsSyncParam.getPageCount(); i++) {
                    jdGoodsSyncParam.setPageIndex(i);
                    appendWork(countDownLatch, jdGoodsSyncParam);
                }
                try {
                    countDownLatch.await(300L,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void appendWork(CountDownLatch countDownLatch, JdGoodsSyncParam jdGoodsSyncParam) {
        boolean workAppended = false;
        while (!workAppended) {
            if (jdGoodsSyncParam.getPageIndex() > 0) {
                if (threadPoolUtil.canAppendWork()) {
                    workAppended = true;
                    threadPoolUtil.getThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            log.info("请求：poolWorkQueue=" + threadPoolUtil.getThreadPool().getQueue().size() + ",eliteId=" + jdGoodsSyncParam.getEliteId() + ",总页数=" + jdGoodsSyncParam.getTotalCount() + ",当前页=" + jdGoodsSyncParam.getPageIndex());
                            ResultInfo<JdGoodsSyncParam> response = jdGoodsService.syncGoods(jdGoodsSyncParam);
                            if (response.isSuccess()) {
                                countDownLatch.countDown();
                                log.info("结果：countDown=" + countDownLatch.getCount() + ",返回数量" + response.getData() + ",总页数=" + response.getData().getPageCount() + ",当前页=" + response.getData().getPageIndex());
                            } else {
                                //遇到失败，失败页重新入队
                                appendWork(countDownLatch, response.getData());
                                log.error("请求京东接口返回失败[已重新入队列]，请求信息：" + JSON.toJSONString(response.getData()) + ",返回信息：" + response.getMsg());
                            }

                        }
                    });
                } else {
                    appendWork(countDownLatch, jdGoodsSyncParam);
                }
            }
        }
    }

}
