package com.platform.common.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Huangyonghao on 2019/8/28 14:35.
 */
@Getter
@Setter
public class MyCategory implements Serializable {

    private long myCategoryId;
    private String myCategoryName;
    private LocalDateTime updateTime;
}
