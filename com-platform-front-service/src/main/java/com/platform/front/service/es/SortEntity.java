package com.platform.front.service.es;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.search.sort.SortOrder;

@Getter
@Setter
public class SortEntity {
    private String key;
    private SortOrder sortOrder;

    public SortEntity(){

    }
    public SortEntity(String key, SortOrder sortOrder){
        this.key = key;
        this.sortOrder = sortOrder;
    }

}
