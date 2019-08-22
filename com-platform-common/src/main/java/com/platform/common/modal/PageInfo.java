package com.platform.common.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Huangyonghao on 2019/6/18 15:57.
 */
@Getter
@Setter
public class PageInfo  implements Serializable {
    private int pageSize = 25;
    private int pageIndex = 1;

    public int getPageCount(int totalCount) {
        return (totalCount  +  pageSize  - 1) / pageSize;
    }
}
