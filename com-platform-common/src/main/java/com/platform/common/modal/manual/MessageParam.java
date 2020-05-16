package com.platform.common.modal.manual;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jkddg
 */
@Getter
@Setter
public class MessageParam implements Serializable {

    private String id;
    private List<String> imgUrls;
    private String msg;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private int maxSendCount;
    private long createUser;
    private String createUserName;

}
