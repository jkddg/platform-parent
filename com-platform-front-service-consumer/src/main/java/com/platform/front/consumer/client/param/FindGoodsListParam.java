package com.platform.front.consumer.client.param;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 14:45.
 */
@Getter
@Setter
public class FindGoodsListParam extends PageInfo implements Serializable {

    private List<PlatformEnum> platformEnum;
    private String keyWord;
    /**
     * 1、佣金比例
     * 2、券额度比例
     * 3、券面值
     * 4、销量
     * 5、价格
     */
    private int sort;
}
