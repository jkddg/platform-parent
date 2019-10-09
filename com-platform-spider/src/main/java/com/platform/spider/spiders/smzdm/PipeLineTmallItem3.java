package com.platform.spider.spiders.smzdm;

import com.platform.spider.spiderCore.ResponsePipeLine;
import com.platform.spider.spiderCore.SpiderResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Huangyonghao on 2019/10/9 14:57.
 */
public class PipeLineTmallItem3 extends ResponsePipeLine {
    @Override
    public void processResponse(SpiderResponse response) {
        Document doc = Jsoup.parse(response.getText());
        Element element = doc.selectFirst("div.J_info");
        element = trySelectFirst(element, "a.img-box");
        if (element != null) {
            String url = this.trim(element.attr("href"));
            new SpiderSmzdmTmallItem(url, response.getReferer(), new PipeLineTmallItem3());
        }
    }
}
