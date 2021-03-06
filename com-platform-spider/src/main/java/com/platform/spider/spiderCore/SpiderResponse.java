package com.platform.spider.spiderCore;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class SpiderResponse {
    private CloseableHttpResponse response;
    private Document doc;
    private String text;
    private String referer;
    private Map<String, String> meta;

    public SpiderResponse(CloseableHttpResponse response, String referer, Map<String, String> meta) {
        this.response = response;
        this.meta = meta;
        this.referer = referer;
        try {
            this.text = EntityUtils.toString(response.getEntity());
            this.doc = Jsoup.parse(this.text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getReferer() {
        return this.referer;
    }

    public Document getDocument() {
        return doc;
    }

    public String getHtml() {
        return text;
    }

    public String getText() {
        return text;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
}
