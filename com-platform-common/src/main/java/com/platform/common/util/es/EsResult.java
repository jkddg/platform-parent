package com.platform.common.util.es;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EsResult<T> {
    private long total;
    private List<T> data;
}
