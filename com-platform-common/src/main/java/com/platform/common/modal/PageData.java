package com.platform.common.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 16:01.
 */
@Getter
@Setter
public class PageData<T> extends ResultInfo implements Serializable {
    private List<T> data;
    private long totalCount = 0;
}
