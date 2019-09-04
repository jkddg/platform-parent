package com.platform.spider.spiderCore.middle;

import com.platform.spider.spiderCore.Spider;

public abstract class MiddleHttpProxyHandler {

    /**
     *@describe 返回一个长度为4的数组，[user,password,host,port]
     *@param  spider
     *@return  String[]
     *@author  mtime
     *@date  2018/7/25 0025
     */
    public abstract String[] processRequest(Spider spider);

}
