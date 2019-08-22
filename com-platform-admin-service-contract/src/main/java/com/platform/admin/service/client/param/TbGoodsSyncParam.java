package com.platform.admin.service.client.param;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TbGoodsSyncParam implements Serializable {
    private int pageSize = 25;
    private int pageIndex;
    private long totalCount;
    private boolean hasCoupon;
    private String categorys;
    private long materialId;
    private String keyWord;

    public long getPageCount() {
        return (totalCount + pageSize - 1) / pageSize;
    }

    private String sortName;
    private String sort;

    @Override
    public TbGoodsSyncParam clone() {
        return JSON.parseObject(JSON.toJSONString(this), this.getClass());
    }
}
