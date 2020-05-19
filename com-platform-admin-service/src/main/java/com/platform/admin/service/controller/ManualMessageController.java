package com.platform.admin.service.controller;

import com.platform.admin.service.iface.ManualMessageService;
import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.manual.MessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jkddg
 */
@RestController
@RequestMapping("/manual/message")
public class ManualMessageController {

    @Autowired
    ManualMessageService manualMessageService;

    @RequestMapping("add")
    public ResultInfo addMessage(MessageParam messageParam) {
        ResultInfo resultInfo = manualMessageService.add(messageParam);
        return resultInfo;
    }
}
