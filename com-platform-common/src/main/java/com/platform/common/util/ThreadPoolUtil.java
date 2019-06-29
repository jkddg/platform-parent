package com.platform.common.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolUtil {

    private static ThreadPoolExecutor fixedThreadPool = null;
    private static Object lockObj = new Object();
    private static final int nThreads = 5;
    private static final int maxQueueSize = 10000;

    public ThreadPoolExecutor getThreadPool() {

        if (fixedThreadPool == null) {
            synchronized (lockObj) {
                if (fixedThreadPool == null) {
                    fixedThreadPool = new ThreadPoolExecutor(nThreads, nThreads,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>());
                }
            }
        }
        return fixedThreadPool;
    }

    /**
     * 判断线程池队列是不是超过一定数量，超过就暂停往线程池放任务
     * @return
     */
    public boolean canAppendWork() {
        if (fixedThreadPool == null) {
            getThreadPool();
        }
        if (fixedThreadPool.getQueue().size() > maxQueueSize) {
            return false;
        }

        return true;
    }
}
