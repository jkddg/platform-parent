package com.platform.common.modal.manual;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ManualMessage implements Serializable {
    private String id;
    private long myCategoryId;
    private String myCategoryName;
    private int platformId;
    private String platformName;
    private String imgUrl;
    private String msg;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private LocalDateTime lastSendTime;
    private int maxSendCount;
    private int beenSendCount = 0;
    private String createUser;

}
