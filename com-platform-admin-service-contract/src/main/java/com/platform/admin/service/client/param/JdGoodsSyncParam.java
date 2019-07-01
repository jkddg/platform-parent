package com.platform.admin.service.client.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JdGoodsSyncParam implements Serializable {
    private int eliteId;
    private int pageSize = 25;
    private int pageIndex;
    private long totalCount;

    public long getPageCount() {
        return (totalCount + pageSize - 1) / pageSize;
    }

    private String sortName;
    private String sort;
}
