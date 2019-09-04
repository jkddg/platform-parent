package com.platform.spider.spiderCore;

import com.alibaba.fastjson.JSONObject;

public interface PipeLines {

    default void openItem(Spider spider) {
    }

    JSONObject processItem(JSONObject item, Spider spider);

    default void closeItem(Spider spider) {
    }

}
