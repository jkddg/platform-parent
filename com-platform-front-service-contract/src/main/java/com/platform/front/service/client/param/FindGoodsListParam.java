package com.platform.front.service.client.param;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.PageInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Huangyonghao on 2019/6/18 14:45.
 */
@Getter
@Setter
public class FindGoodsListParam extends PageInfo {

    private PlatformEnum platformEnum;
    private String keyWord;
}
