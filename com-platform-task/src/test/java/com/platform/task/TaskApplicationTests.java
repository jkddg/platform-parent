package com.platform.task;

import com.platform.task.biz.JdGoodsBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TaskApplication.class)
public class TaskApplicationTests {

    @Autowired
    JdGoodsBiz jdGoodsBiz;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testSyncJdGoods(){
        jdGoodsBiz.syncGoods();
    }
}
