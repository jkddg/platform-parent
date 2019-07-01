package com.platform.task.biz;

import com.platform.admin.service.iface.JdGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JdGoodsBiz {

    @Autowired
    JdGoodsService jdGoodsService;


}
