package com.platform.task.biz;

import com.alibaba.fastjson.JSON;
import com.platform.admin.service.client.param.TbGoodsSyncParam;
import com.platform.admin.service.iface.TbGoodsService;
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
public class TbGoodsBiz {

    private boolean lockObj = false;

    @Autowired
    TbGoodsService tbGoodsService;


    @Autowired
    ThreadPoolUtil threadPoolUtil;

    private long maxPageCount = 200;

    private long errorCount = 0;
    private static final long maxErrorCount = 100;

    public void syncGoods() {
        if (lockObj) {
            return;
        }
        lockObj = true;
        List<String> keywords = new ArrayList<>();
        keywords.add("休闲食品");
        keywords.add("小吃");
        keywords.add("月饼");
//        keywords.add("T恤");
//        keywords.add("凉鞋");
//        keywords.add("纸品湿巾");
//        keywords.add("男士内裤");
        keywords.add("数码配件");
//        keywords.add("旅行箱");
//        keywords.add("床品套件");
        keywords.add("健身器材");
        keywords.add("洗发护发");
        keywords.add("洁面");
//        keywords.add("大家电");
        keywords.add("坚果蜜饯");
//        keywords.add("POLO衫");
        keywords.add("运动鞋");
        keywords.add("图书文具");
        keywords.add("男袜");
        keywords.add("电脑配件");
//        keywords.add("双肩包");
        keywords.add("水具酒具");
        keywords.add("玩转球类");
        keywords.add("沐浴用品");
        keywords.add("面霜");
        keywords.add("厨卫大电");
        keywords.add("糕点饼干");
//        keywords.add("衬衫");
        keywords.add("帆布鞋");
        keywords.add("医药保健");
        keywords.add("睡衣睡裤");
//        keywords.add("手机相机");
        keywords.add("钱包");
        keywords.add("厨卫净化");
        keywords.add("运动服饰");
        keywords.add("牙膏牙刷");
        keywords.add("水乳");
        keywords.add("厨卫小电");
        keywords.add("方便速食");
        keywords.add("卫衣");
        keywords.add("板鞋");
        keywords.add("节庆用品");
        keywords.add("背心汗衫");
//        keywords.add("电脑整机");
        keywords.add("首饰");
        keywords.add("生活日用");
        keywords.add("瑜伽舞蹈");
        keywords.add("发蜡发胶");
        keywords.add("精华");
        keywords.add("生活电器");
        keywords.add("新鲜果蔬");
        keywords.add("外套");
        keywords.add("休闲鞋");
        keywords.add("车载用品");
        keywords.add("家居服");
        keywords.add("电脑外设");
        keywords.add("手表");
        keywords.add("厨卫用品");
        keywords.add("运动鞋");
        keywords.add("护手霜");
        keywords.add("防晒");
        keywords.add("个护健康");
        keywords.add("茶水冲饮");
        keywords.add("背心");
        keywords.add("皮鞋");
        keywords.add("礼品工艺");
        keywords.add("打底裤");
        keywords.add("游戏相关");
        keywords.add("皮带");
        keywords.add("收纳清洁");
        keywords.add("运动包");
        keywords.add("身体护理");
        keywords.add("面膜");
        keywords.add("电风扇");
        keywords.add("粮油调味");
        keywords.add("短裤");
        keywords.add("篮球鞋");
        keywords.add("绿植园艺");
        keywords.add("男士短裤");
        keywords.add("网络设备");
        keywords.add("单肩包");
        keywords.add("灯具灯饰");
        keywords.add("游泳健将");
        keywords.add("足部护理");
        keywords.add("身体乳");
        keywords.add("剃须刀");
        keywords.add("糖果布丁");
        keywords.add("牛仔裤");
        keywords.add("靴子");
        keywords.add("文娱乐器");
        keywords.add("情趣内衣");
        keywords.add("智能电子");
        keywords.add("眼镜");
        keywords.add("家装软饰");
        keywords.add("户外");
        keywords.add("洗衣液");
        keywords.add("润唇");
        keywords.add("干衣干鞋");
        keywords.add("酒类");
        keywords.add("休闲裤");
        keywords.add("布鞋");
        keywords.add("宠物生活");
        keywords.add("大码");
        keywords.add("影音播放");
        keywords.add("帽子");
        keywords.add("家装家具");
        keywords.add("骑行装备");
        keywords.add("漱口水");
        keywords.add("护肤套装");
        keywords.add("电动牙刷");
        keywords.add("营养保健");
        keywords.add("夹克");
        keywords.add("豆豆鞋");
        keywords.add("计生情趣");
        keywords.add("中老年");
        keywords.add("储存设备");
        keywords.add("挂饰");
        keywords.add("五金工具");
        keywords.add("垂钓");
        keywords.add("隐形眼镜");
        keywords.add("眼部护理");
        keywords.add("电水壶");
        keywords.add("网红零食");
        keywords.add("防晒衣");
        keywords.add("轻松长高");
        keywords.add("健康加倍");
        keywords.add("居家睡袍");
        keywords.add("数据线");
        keywords.add("商务男士");
        keywords.add("浪漫香蕉");
        keywords.add("居家健身");
        keywords.add("衣物香氛");
        keywords.add("畅通呼吸");
        keywords.add("健康每一天");
        keywords.add("速干衣");
        keywords.add("爸爸专享");
        keywords.add("清洁必备");
        keywords.add("功能保健");
        keywords.add("手机膜");
        keywords.add("大牌钱包");
        keywords.add("裸睡神器");
        keywords.add("减脂增肌");
        keywords.add("清新口气");
        keywords.add("自制火锅");
        keywords.add("10元一箩筐");
        keywords.add("格子衬衫");
        keywords.add("英伦工装");
        keywords.add("学生必备");
        keywords.add("手机壳");
        keywords.add("珠宝手串");
        keywords.add("收纳助手");
        keywords.add("运动耳机");
        keywords.add("私密护理");
        keywords.add("早饭不愁");
        keywords.add("营养早餐");
        keywords.add("运动套装");
        keywords.add("雨雪出行");
        keywords.add("情趣狂欢");
        keywords.add("耳朵福利");
        keywords.add("网红箱包");
        keywords.add("懒人绿植");
        keywords.add("塑身");
        keywords.add("拒绝脱发");
        keywords.add("家务帮手");
        keywords.add("边吃边瘦");
        keywords.add("西服西裤");
        keywords.add("户外专用");
        keywords.add("杂货小铺");
        keywords.add("自拍达人");
        keywords.add("精品书包");
        keywords.add("防蚊驱虫");
        keywords.add("长跑一族");
        keywords.add("呵护双手");
        keywords.add("美体养生");
        keywords.add("汽车");
        keywords.add("车载");
        for (String keyWord : keywords) {
            errorCount = 0;
            long pageCount = 0;
            CountDownLatch countDownLatch;
            TbGoodsSyncParam tbGoodsSyncParam = new TbGoodsSyncParam();
            tbGoodsSyncParam.setKeyWord(keyWord);
            tbGoodsSyncParam.setPageIndex(1);
            tbGoodsSyncParam.setPageSize(40);
            tbGoodsSyncParam.setHasCoupon(true);
            tbGoodsSyncParam.setSort("total_sales");
            ResultInfo<TbGoodsSyncParam> response = tbGoodsService.syncGoods(tbGoodsSyncParam);
            if (response.isSuccess()) {
                tbGoodsSyncParam = response.getData();
                if (tbGoodsSyncParam.getPageCount() > maxPageCount) {
                    pageCount = maxPageCount;
                } else {
                    pageCount = tbGoodsSyncParam.getPageCount();
                }
                countDownLatch = new CountDownLatch((int) pageCount - 1);
                for (int i = 2; i <= pageCount; i++) {
                    TbGoodsSyncParam param = tbGoodsSyncParam.clone();
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

    private void appendWork(CountDownLatch countDownLatch, TbGoodsSyncParam tbGoodsSyncParam) {
        boolean workAppended = false;
        while (!workAppended) {
            if (tbGoodsSyncParam.getPageIndex() > 0) {
                if (threadPoolUtil.canAppendWork()) {
                    workAppended = true;
                    threadPoolUtil.getThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            log.info("淘宝请求：poolWorkQueue=" + threadPoolUtil.getThreadPool().getQueue().size() + ",categorys=" + tbGoodsSyncParam.getCategorys() + ",keyWord=" + tbGoodsSyncParam.getKeyWord() + ",总页数=" + tbGoodsSyncParam.getPageCount() + ",当前页=" + tbGoodsSyncParam.getPageIndex());
                            ResultInfo<TbGoodsSyncParam> response = tbGoodsService.syncGoods(tbGoodsSyncParam);
                            if (response.isSuccess()) {
                                countDownLatch.countDown();
                                log.info("淘宝结果：countDown=" + countDownLatch.getCount() + ",返回数量" + response.getData() + ",总页数=" + response.getData().getPageCount() + ",当前页=" + response.getData().getPageIndex());
                            } else {
                                errorCount++;
                                if (errorCount > maxErrorCount) {
                                    return;
                                }
                                //遇到失败，失败页重新入队
                                appendWork(countDownLatch, response.getData());
                                log.error("请求淘宝接口返回失败[已重新入队列]，请求信息：" + JSON.toJSONString(response.getData()) + ",返回信息：" + response.getMsg());

                            }

                        }
                    });
                } else {
                    appendWork(countDownLatch, tbGoodsSyncParam);
                }
            }
        }
    }

}
