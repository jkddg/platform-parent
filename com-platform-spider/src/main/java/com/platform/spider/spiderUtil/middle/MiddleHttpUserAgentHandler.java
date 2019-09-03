package com.platform.spider.spiderUtil.middle;

import com.platform.spider.spiderUtil.Spider;
import com.platform.spider.spiderUtil.SpiderRequest;

public abstract class MiddleHttpUserAgentHandler {

    /**
     *@describe 生成一个UA
     *@param  request
     *@param  spider
     *@return  String
     *@author  mtime
     *@date  2018/7/25 0025
     */
    public abstract String processRequest(SpiderRequest request, Spider spider);

}

