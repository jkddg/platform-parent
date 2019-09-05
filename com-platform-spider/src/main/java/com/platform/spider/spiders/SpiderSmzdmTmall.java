package com.platform.spider.spiders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.spider.constant.SpiderName;
import com.platform.spider.spiderCore.Spider;
import com.platform.spider.spiderCore.SpiderResponse;
import com.platform.spider.spiderCore.constant.AcceptType;
import com.platform.spider.spiderCore.constant.RequestMethod;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/9/4 13:27.
 */
@Slf4j
public class SpiderSmzdmTmall extends Spider {

    public SpiderSmzdmTmall() {
        super(SpiderName.SMZDM_TMALL, RequestMethod.GET, "utf-8");
        String url = "https://www.smzdm.com/fenlei/shipinbaojian/h1c4s247f0t0p2";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", AcceptType.ALL);
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Host", "www.smzdm.com");
        headers.put("Proxy-Connection", "keep-alive");
//        headers.put("Referer", "http://www.dianping.com/beijing");
//        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
//        headers.put("X-Requested-With", "XMLHttpRequest");
        this.url = url;
        this.headers = headers;

        this.yield(this);
    }

    @Override
    public void responseCallback(SpiderResponse response) {
        log.info(response.getText());
        JSONObject jsonObject = JSON.parseObject(response.getText());
        if (jsonObject.containsKey("data")) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray citys = data.getJSONArray("all");
            for (int i = 0; i < citys.size(); i++) {
                JSONObject cityObj = citys.getJSONObject(i);
                this.processItem(cityObj);
            }
        }
    }

    @Override
    public JSONObject processItem(JSONObject item) {
        return item;
    }

    public static void main(String[] args) {
        new SpiderSmzdmTmall().startRequests();
    }


}
