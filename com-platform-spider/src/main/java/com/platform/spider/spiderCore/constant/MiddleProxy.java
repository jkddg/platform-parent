package com.platform.spider.spiderCore.constant;


import com.platform.spider.spiderCore.Spider;

/**
 * @author mtime
 * @version 1.0
 * @date 2018/7/25 0025
 */
public class MiddleProxy  {


    public String[] processRequest(Spider spider) {
        String[][] proxies = {
//                {"H84D338NX736437D", "E223D6240DC64E28", "http-dyn.abuyun.com", "9020"},
//                {"HU3H2KZDEHXHX85D", "ECF7812199097BBC", "http-dyn.abuyun.com", "9020"}
        };
        return proxies[(int) (Math.random() * proxies.length)];
    }

}
