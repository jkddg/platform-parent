package com.platform.common.modal.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfo implements Serializable {
    private long userId;
    private String userName;
    private String userPwd;
    private String realName;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
