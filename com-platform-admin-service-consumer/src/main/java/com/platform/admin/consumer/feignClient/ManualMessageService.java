package com.platform.admin.consumer.feignClient;


import com.platform.common.modal.ResultInfo;
import com.platform.common.modal.manual.MessageParam;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jkddg
 */
@FeignClient(value = "com-platform-admin-service", fallbackFactory = ManualMessageFallbackFactory.class)
public interface ManualMessageService {
    @PostMapping("/manual/message/add")
    ResultInfo add(MessageParam messageParam);
}


@Component
class ManualMessageFallbackFactory implements FallbackFactory<ManualMessageService> {

    private Logger logger = LoggerFactory.getLogger(ManualMessageFallbackFactory.class);

    @Override
    public ManualMessageService create(Throwable cause) {
        return new ManualMessageService() {
            @Override
            public ResultInfo add(MessageParam messageParam) {
                logger.error("调用ManualMessageService.add失败：" + cause.getMessage() + cause.getStackTrace());
                System.out.println("调用ManualMessageService.add失败：" + cause.getMessage() + cause.getStackTrace());
                ResultInfo resultInfo = ResultInfo.failInfo("请求失败");
                return resultInfo;
            }

        };
    }
}
