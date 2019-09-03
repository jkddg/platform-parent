package com.platform.spider.spiderUtil;

import java.util.HashMap;
import java.util.Map;

public class StatisticsInfo {
    //开始，结束时间
    private long startTime = 0L;
    private long finishTime = 0L;

    //状态统计信息
    private Map<String, Integer> statusMap = new HashMap<>();

    //
    private String finishReason = "";

    public StatisticsInfo() {
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public void addStatus(String status) {
        if (!this.statusMap.containsKey(status)) {
            this.statusMap.put(status, 1);
        } else {
            this.statusMap.put(status, 1 + this.statusMap.get(status));
        }
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public String getStatistics() {
        return "";
    }
}
