package com.platform.common.util.es;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RangeQueryEntity {
    private String fieldName;
    private Object minValue;
    private Object maxValue;
}
