package com.platform.spider.spiderUtil;

import java.util.ArrayList;
import java.util.List;

public interface SpiderIpl {
    String getName();

    default String getCustomSettings() {
        return "{}";
    }

    default List<String> getDomains() {
        return new ArrayList<>();
    }
}
