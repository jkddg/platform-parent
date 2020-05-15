package com.platform.admin.service.controller;

import com.platform.common.modal.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jkddg
 */
@RestController
@RequestMapping("/manual/message")
public class ManualMessageController {

    @RequestMapping("add")
    public ResultInfo addMessage(){
        ResultInfo resultInfo=new ResultInfo();

        return resultInfo;
    }
}
