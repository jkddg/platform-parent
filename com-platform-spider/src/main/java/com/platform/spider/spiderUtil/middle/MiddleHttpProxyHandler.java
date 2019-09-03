package com.platform.spider.spiderUtil.middle;

import com.platform.spider.spiderUtil.Spider;
import com.platform.spider.spiderUtil.SpiderRequest;

public abstract class MiddleHttpProxyHandler {

    /**
     *@describe 返回一个长度为4的数组，[user,password,host,port]
     *@param  request
     *@param  spider
     *@return  String[]
     *@author  mtime
     *@date  2018/7/25 0025
     */
    public abstract String[] processRequest(SpiderRequest request, Spider spider);

}
