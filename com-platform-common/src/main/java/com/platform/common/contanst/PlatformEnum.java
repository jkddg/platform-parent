package com.platform.common.contanst;


import com.platform.common.lang.EnumDisplayNameSupport;
import com.platform.common.lang.EnumValueSupport;
import com.platform.common.lang.Enums;

/**
 * Created by Huangyonghao on 2019/6/17 16:25.
 */
public enum  PlatformEnum implements EnumValueSupport, EnumDisplayNameSupport {
    TAOBAO(1, "淘宝"),
    TMALL(2, "天猫"),
    JD(3, "京东");

    private int value;
    private String displayName;

    private PlatformEnum(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    public static PlatformEnum valueOf(int value) {
        return Enums.valueOf(PlatformEnum.class, value);
    }
}
