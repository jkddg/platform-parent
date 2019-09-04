package com.platform.spider.spiderCore.middle;

import com.platform.spider.spiderCore.Spider;

public abstract class MiddleHttpUserAgentHandler {

    /**
     *@describe 生成一个UA
     *@param  spider
     *@return  String
     *@author  mtime
     *@date  2018/7/25 0025
     */
    public abstract String processRequest(Spider spider);

}

