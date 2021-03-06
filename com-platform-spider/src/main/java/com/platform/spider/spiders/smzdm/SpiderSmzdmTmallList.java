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
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/9/4 13:27.
 */
@Slf4j
public class SpiderSmzdmTmallList extends Spider {

    int pagesize = 30;

    public SpiderSmzdmTmallList() {
        super(SpiderName.SMZDM_TMALL, RequestMethod.GET, "utf-8", null);
        String url = "https://www.smzdm.com/fenlei/shipinbaojian/h1c4s247f0t0p2";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", AcceptType.ALL);
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headers.put("Host", "www.smzdm.com");
        headers.put("Proxy-Connection", "keep-alive");
//        headers.put("Referer", "http://www.dianping.com/beijing");
//        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
//        headers.put("X-Requested-With", "XMLHttpRequest");
        this.url = url;
        this.headers = headers;
        this.meta = new HashMap<>();
        this.meta.put("page", "1");
        this.yield(this);
    }

    @Override
    public void responseCallback(SpiderResponse response) {
        int page = 1;
        Map<String, String> meta = response.getMeta();
        if (meta != null) {
            page = Integer.parseInt(meta.get("page"));
        }
        Document doc = Jsoup.parse(response.getText());
        Elements elements = doc.select("ul#feed-main-list").select("li.feed-row-wide");
        if (elements != null && elements.size() > 0) {
            for (Element element : elements) {
                String url = element.selectFirst("div.z-feed-img").selectFirst("a").attr("href");
                 new SpiderSmzdmTmallItem(url,new PipeLineTmallItem1());
                 break;
            }

        }
        if (elements.size() == pagesize) {
            page++;
            recursivePage(meta, page, pagesize);
        }
    }



    private void recursivePage(Map<String, String> meta, int page, int pagesize) {
        long timespan = System.currentTimeMillis();
        String url = String.format("http://dianying.nuomi.com/cinema?pagelets[]=pageletCinemalist@pageletCinema&reqID=%s&cityId=%s&pageSize=%s&pageNum=%s&date=%s&sortType=2&t=%s", page, meta.get("cid"), pagesize, page, timespan, timespan);
        Map<String, String> h = new HashMap<>();
        for (String k : this.headers.keySet()) {
            h.put(k, this.headers.get(k));
        }
//                h.put("Cookie", "ci=" + meta.get("cid"));

        Map<String, String> temp = new HashMap<>();
        temp.put("cid", meta.get("cid"));
        temp.put("cityName", meta.get("cityName"));
        temp.put("page", "" + page);
        meta.put("pinyin", meta.get("pinyin"));
        //this.yield(new Request(url, MethodType.GET, h, null, null, temp, "utf-8", "deal"),this);
    }

    public static void main(String[] args) {
        new SpiderSmzdmTmallList().startRequests();
    }


}
