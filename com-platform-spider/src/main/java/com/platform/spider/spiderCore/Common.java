package com.platform.spider.spiderCore;

import java.util.Comparator;

public class Common {
    public static Comparator<Integer> sortDesc = (Integer o1, Integer o2) -> {
        if (o1 > o2) {
            return 1;
        } else if (o1 < o2) {
            return -1;
        } else {
            return 0;
        }
    };
}
