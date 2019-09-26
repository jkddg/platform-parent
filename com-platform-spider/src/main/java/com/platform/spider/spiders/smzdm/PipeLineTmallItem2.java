package com.platform.spider.spiders.smzdm;

import com.platform.spider.spiderCore.ResponsePipeLine;
import com.platform.spider.spiderCore.SpiderResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Huangyonghao on 2019/9/26 15:56.
 */
public class PipeLineTmallItem2 extends ResponsePipeLine {

    @Override
    public void processResponse(SpiderResponse response) {
        Document doc = Jsoup.parse(response.getText());
        Element element = doc.selectFirst("div.J_info");
        element = trySelectFirst(element, "a.img-box");
        if (element != null) {
            String url = this.trim(element.attr("href"));
            new SpiderSmzdmTmallItem(url);
        }
    }
}