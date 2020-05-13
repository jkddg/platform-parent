package com.platform.admin.service.iface;

import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.manual.MessageParam;

/**
 * @author jkddg
 */
public interface ManualMessageService {
    ResultInfo add(MessageParam messageParam);
}
