package com.platform.common.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Huangyonghao on 2019/8/28 14:32.
 */
@Getter
@Setter
public class GoodsCategory implements Serializable {
    private String id;
    private long myCategoryId;
    private String myCategoryName;
    private int platformId;
    private String platformName;
    private long levelOneCategoryId;
    private String levelOneCategoryName;
    private long categoryId;
    private String categoryName;
    private LocalDateTime updateTime;

}
