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
    private boolean lockObj = false;
    @Autowired
    JdGoodsService jdGoodsService;


    @Autowired
    ThreadPoolUtil threadPoolUtil;
    private long maxPageCount = 500;

    public void syncGoods() {
        if (lockObj) {
            return;
        }
        lockObj = true;
//                1-好券商品,
//                2-京粉APP-jingdong.超级大卖场,
//                3-小程序-jingdong.好券商品,
//                4-京粉APP-jingdong.主题聚惠1-jingdong.服装运动,
//                5-京粉APP-jingdong.主题聚惠2-jingdong.精选家电,
//                6-京粉APP-jingdong.主题聚惠3-jingdong.超市,
//                7-京粉APP-jingdong.主题聚惠4-jingdong.居家生活,
//                10-9.9专区,
//                11-品牌好货-jingdong.潮流范儿,
//                12-品牌好货-jingdong.精致生活,
//                13-品牌好货-jingdong.数码先锋,
//                14-品牌好货-jingdong.品质家电,
//                15-京仓配送,
//                16-公众号-jingdong.好券商品,
//                17-公众号-jingdong.9.9,
//                18-公众号-jingdong.京东配送,
//                22-精品库
        List<Integer> eliteIds = new ArrayList() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
                add(7);
                add(10);
                add(11);
                add(12);
                add(13);
                add(14);
                add(15);
                add(16);
                add(17);
                add(18);
                add(22);

            }
        };
        for (Integer eliteId : eliteIds) {
            long pageCount = 0;
            CountDownLatch countDownLatch;
            JdGoodsSyncParam jdGoodsSyncParam = new JdGoodsSyncParam();
            jdGoodsSyncParam.setEliteId(eliteId);
            jdGoodsSyncParam.setPageIndex(1);
            jdGoodsSyncParam.setPageSize(40);
            ResultInfo<JdGoodsSyncParam> response = jdGoodsService.syncGoods(jdGoodsSyncParam);
            if (response.isSuccess()) {
                jdGoodsSyncParam = response.getData();
                if (jdGoodsSyncParam.getPageCount() > maxPageCount) {
                    pageCount = maxPageCount;
                } else {
                    pageCount = jdGoodsSyncParam.getPageCount();
                }
                countDownLatch = new CountDownLatch((int) pageCount - 1);
                for (int i = 2; i <= pageCount; i++) {
                    JdGoodsSyncParam param = jdGoodsSyncParam.clone();
                    param.setPageIndex(i);
                    appendWork(countDownLatch, param);
                }
                try {
                    countDownLatch.await(300L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        lockObj = false;
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
                            log.info("京东请求：poolWorkQueue=" + threadPoolUtil.getThreadPool().getQueue().size() + ",eliteId=" + jdGoodsSyncParam.getEliteId() + ",总页数=" + jdGoodsSyncParam.getPageCount() + ",当前页=" + jdGoodsSyncParam.getPageIndex());
                            ResultInfo<JdGoodsSyncParam> response = jdGoodsService.syncGoods(jdGoodsSyncParam);
                            if (response.isSuccess()) {
                                countDownLatch.countDown();
                                log.info("京东结果：countDown=" + countDownLatch.getCount() + ",返回数量" + response.getData() + ",总页数=" + response.getData().getPageCount() + ",当前页=" + response.getData().getPageIndex());
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
