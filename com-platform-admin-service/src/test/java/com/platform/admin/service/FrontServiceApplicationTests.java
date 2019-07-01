package com.platform.admin.service;

import com.platform.front.service.iface.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AdminServiceApplication.class)
public class FrontServiceApplicationTests {

    @Autowired
    GoodsService taobaoGoodsService;

    @Test
    public void contextLoads() {
    }

}
