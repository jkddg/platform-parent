package com.platform.spider.spiderCore;

import org.jsoup.nodes.Element;

public abstract class ResponsePipeLine {

    public abstract void processResponse(SpiderResponse response);

    protected Element trySelectFirst(Element element, String selecter) {
        if (element != null) {
            element = element.selectFirst(selecter);
            return element;
        }
        return element;
    }
    protected String trim(String message) {
        if (message != null) {
            return message.trim();
        } else {
            return "";
        }
    }
}
