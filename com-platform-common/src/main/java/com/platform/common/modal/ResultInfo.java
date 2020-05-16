package com.platform.common.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Huangyonghao on 2019/6/18 16:07.
 */
@Getter
@Setter
public class ResultInfo<T>  implements Serializable {
    private boolean success = false;
    private String msg;
    private T data;
    public static ResultInfo failInfo(String errmsg){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setSuccess(false);
        resultInfo.setMsg(errmsg);
        return resultInfo;
    }
}
