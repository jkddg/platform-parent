package com.platform.admin.service.impl;

import com.platform.admin.service.biz.ManualMessageServiceBiz;
import com.platform.admin.service.iface.ManualMessageService;
import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.manual.MessageParam;

/**
 * @author jkddg
 */
public class ManualMessageServiceImpl implements ManualMessageService {
    ManualMessageServiceBiz manualMessageServiceBiz;

    @Override
    public ResultInfo add(MessageParam messageParam) {
        return manualMessageServiceBiz.add(messageParam);
    }
}
