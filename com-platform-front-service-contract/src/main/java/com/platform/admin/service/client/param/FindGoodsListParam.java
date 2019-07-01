package com.platform.admin.service.client.param;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Huangyonghao on 2019/6/18 14:45.
 */
@Getter
@Setter
public class FindGoodsListParam extends PageInfo implements Serializable {

    private PlatformEnum platformEnum;
    private String keyWord;
    private int sort;
}
