package com.platform.common.util.es;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EsSearchResult {
    private long total;
    private List<String> data;
}
