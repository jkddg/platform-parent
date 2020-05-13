package com.platform.admin.service;

import com.platform.admin.service.iface.JdGoodsService;
import com.platform.common.modal.ResultInfo;
import com.platform.common.param.admin.JdGoodsSyncParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AdminServiceApplication.class)
public class AdminServiceApplicationTests {


    @Autowired
    JdGoodsService jdGoodsService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testJdQuery(){
        JdGoodsSyncParam jdGoodsSyncParam=new JdGoodsSyncParam();
        jdGoodsSyncParam.setEliteId(1);
        jdGoodsSyncParam.setPageIndex(1);

        ResultInfo<JdGoodsSyncParam> resultInfo = jdGoodsService.syncGoods(jdGoodsSyncParam);

    }

}
