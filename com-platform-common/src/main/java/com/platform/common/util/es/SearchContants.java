package com.platform.common.util.es;

/**
 * 搜索常量类
 * Created by kevin.bao on 2019/3/4.
 */
public class SearchContants {
    private SearchContants() {
    }

    /**
     * 粗粒度分词类型 ik_smart
     */
    public static final String ANALYZER_IK_SMART = "ik_smart";
    /**
     * 细粒度分词类型 ik_max_word
     */
    public static final String ANALYZER_IK_MAX_WORD = "ik_max_word";
}
