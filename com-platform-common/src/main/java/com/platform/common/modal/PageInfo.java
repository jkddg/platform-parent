package com.platform.common.modal;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Huangyonghao on 2019/6/18 15:57.
 */
@Getter
@Setter
public class PageInfo {
    private long pageSize = 25;
    private long pageIndex = 1;

    public long getPageCount(long totalCount) {
        return (totalCount  +  pageSize  - 1) / pageSize;
    }
}
