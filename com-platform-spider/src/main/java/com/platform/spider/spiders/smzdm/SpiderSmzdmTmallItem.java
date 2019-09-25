package com.platform.spider.spiders.smzdm;

import com.platform.spider.constant.SpiderName;
import com.platform.spider.spiderCore.Spider;
import com.platform.spider.spiderCore.SpiderResponse;
import com.platform.spider.spiderCore.constant.AcceptType;
import com.platform.spider.spiderCore.constant.RequestMethod;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/9/25 16:09.
 */

@Slf4j
public class SpiderSmzdmTmallItem extends Spider {
    public SpiderSmzdmTmallItem(String url) {

        super(SpiderName.SMZDM_TMALL, RequestMethod.GET, "utf-8", null);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", AcceptType.ALL);
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Host", "www.smzdm.com");
        headers.put("Proxy-Connection", "keep-alive");
        this.url = url;
        this.headers = headers;

        this.yield(this);
        this.startRequests();
    }

    @Override
    public void responseCallback(SpiderResponse response) {

        Document doc = Jsoup.parse(response.getText());
        Element element = doc.selectFirst("div.J_info").selectFirst("li.feed-row-wide").selectFirst("a.img-box");
        if (element != null) {
            String url = this.trim(element.attr("href"));
            new SpiderSmzdmTmallItem(url);
        }

    }
}
