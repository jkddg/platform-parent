package com.platform.front.service.es;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EsResult<T> {
    private long total;
    private List<T> data;
}
