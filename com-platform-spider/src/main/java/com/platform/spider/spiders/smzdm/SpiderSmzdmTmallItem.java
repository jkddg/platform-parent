package com.platform.spider.spiders.smzdm;

import com.platform.spider.constant.SpiderName;
import com.platform.spider.spiderCore.ResponsePipeLine;
import com.platform.spider.spiderCore.Spider;
import com.platform.spider.spiderCore.SpiderResponse;
import com.platform.spider.spiderCore.constant.AcceptType;
import com.platform.spider.spiderCore.constant.RequestMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huangyonghao on 2019/9/25 16:09.
 */

@Slf4j
public class SpiderSmzdmTmallItem extends Spider {

    public SpiderSmzdmTmallItem(String url, ResponsePipeLine... responsePipeLines) {
        super(SpiderName.SMZDM_TMALL, RequestMethod.GET, "utf-8", null);
        initSpider(url, null, responsePipeLines);
    }

    public SpiderSmzdmTmallItem(String url, String refer, ResponsePipeLine... responsePipeLines) {
        super(SpiderName.SMZDM_TMALL, RequestMethod.GET, "utf-8", null);
        initSpider(url, refer, responsePipeLines);

    }

    private void initSpider(String url, String refer, ResponsePipeLine... responsePipeLines) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", AcceptType.ALL);
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        //headers.put("Host", "www.smzdm.com");
        headers.put("Proxy-Connection", "keep-alive");
        if (!StringUtils.isEmpty(refer)) {
            headers.put("Referer", refer);
        }
        this.url = url;
        this.headers = headers;
        this.responsePipeLines = responsePipeLines;
        this.yield(this);
        this.startRequests();
    }

    @Override
    public void responseCallback(SpiderResponse response) {

        if (this.responsePipeLines != null && this.responsePipeLines.length > 0) {
            for (ResponsePipeLine pipeLine : this.responsePipeLines) {
                pipeLine.processResponse(response);
            }
        }


    }


}
