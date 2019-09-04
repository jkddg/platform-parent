package com.platform.spider.spiderCore.setting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingDefault {
    //下载器超时时间(单位: 秒)
    public static double DOWNLOAD_TIMEOUT = 10;

    //下载器在下载同一个网站下一个页面前需要等待的时间。该选项可以用来限制爬取速度， 减轻服务器压力。
    public static double DOWNLOAD_DELAY = 0;

    //Request重试参数设置
    public static boolean RETRY_ENABLED = true;
    //不切换代理的情况下的重试
    public static int RETRY_TIMES = 0;

    //整体重试
    public static int RETRY_COUNTS = 0;

    //同时下载数量
    public static int CONCURRENT_REQUESTS = 1;

    //user-agents
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

    public static String[] HANDLE_HTTPSTATUS_LIST = {};
}
