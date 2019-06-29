package com.platform.front.service.client.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JdGoodsSyncParam implements Serializable {
    private int eliteId;
    private int pageSize;
    private int pageIndex;
    private long totalCount;
    private String sortName;
    private String sort;
}
