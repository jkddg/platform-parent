package com.platform.spider.spiders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.spider.constant.SpiderName;
import com.platform.spider.spiderCore.constant.AcceptType;
import com.platform.spider.spiderCore.constant.RequestMethod;
import com.platform.spider.spiderCore.Spider;
import com.platform.spider.spiderCore.SpiderResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/9/4 13:27.
 */
@Slf4j
public class SpiderSmzdmTmall extends Spider {
    @Override
    public String getName() {
        return SpiderName.SMZDM_TMALL;
    }


    public void SpiderSmzdmTmall() {
        String url = "https://www.smzdm.com/fenlei/shipinbaojian/h1c4s247f0t0p2";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", AcceptType.HTML);
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Host", "smzdm.com");
        headers.put("Proxy-Connection", "keep-alive");
//        headers.put("Referer", "http://www.dianping.com/beijing");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        headers.put("X-Requested-With", "XMLHttpRequest");

        this.setSpiderRequest(new SpiderRequest(url, RequestMethod.GET, headers, null, null, null, "utf-8", "deal"));
        this.yield(this);
    }

    public void deal(SpiderResponse response) {
        log.info(response.getText());
        JSONObject jsonObject = JSON.parseObject(response.getText());
        if (jsonObject.containsKey("data")) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray citys = data.getJSONArray("all");
            for (int i = 0; i < citys.size(); i++) {
                JSONObject cityObj = citys.getJSONObject(i);
                this.yield(cityObj, this);
            }
        }
    }

    public static void main(String[] args) {
        new SpiderSmzdmTmall().startRequests();
    }


}
