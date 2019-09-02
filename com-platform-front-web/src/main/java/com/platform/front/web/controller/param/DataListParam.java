package com.platform.front.web.controller.param;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by Huangyonghao on 2019/6/21 13:13.
 */
@Getter
@Setter
public class DataListParam {
    private String keyWord;
    private String platform;
    private int sort;
    private int page;
    private int pageSize = 25;
}
