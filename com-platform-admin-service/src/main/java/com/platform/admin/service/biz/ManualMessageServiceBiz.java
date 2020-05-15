package com.platform.admin.service.biz;

import com.platform.common.contanst.EsConstanst;
import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.manual.MessageParam;
import com.platform.common.util.es.EsWriteUtil;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.Environment;
import org.springframework.stereotype.Service;

/**
 * @author jkddg
 */
@Slf4j
@Service
public class ManualMessageServiceBiz {

    public ResultInfo add(MessageParam messageParam) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            EsWriteUtil.set(messageParam, EsConstanst.ES_MANUAL_MESSAGE_INDEX_NAME);
            resultInfo.setSuccess(true);
        } catch (Exception ex) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg(ex.getMessage());
            log.error(ex.getMessage() + ex.getStackTrace());
        }
        return resultInfo;
    }
}
