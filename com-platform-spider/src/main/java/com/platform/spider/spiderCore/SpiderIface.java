package com.platform.spider.spiderCore;

import java.util.ArrayList;
import java.util.List;

public interface SpiderIface {
    String getName();

    default String getCustomSettings() {
        return "{}";
    }

    default List<String> getDomains() {
        return new ArrayList<>();
    }
}
