package com.platform.common.util;

/**
 * Created by Huangyonghao on 2019/8/27 16:59.
 */
public class StringUtil {

    public static String getHttpUrl(String url) {
        if (url.startsWith("http:")) {
            return url;
        }
        if (url.startsWith("//")) {
            return "http:" + url;
        }
        if (url.startsWith("https") || url.startsWith("HTTPS")) {
            return "http" + url.substring(5);
        }
        return url;
    }

    public static String getHttpsUrl(String url) {
        if (url.startsWith("https:")) {
            return url;
        }
        if (url.startsWith("//")) {
            return "https:" + url;
        }
        if (url.startsWith("http:") || url.startsWith("HTTP:")) {
            return "https" + url.substring(4);
        }
        return url;
    }

    public static void main(String[] args) {
        String aa="//asdfasf.com/asdfas";
        String bb="http://asdfasf.com/asdfas";
        String cc="https://asdfasf.com/asdfas";

        System.out.println(getHttpUrl(aa));
        System.out.println(getHttpUrl(bb));
        System.out.println(getHttpUrl(cc));

        System.out.println(getHttpsUrl(aa));
        System.out.println(getHttpsUrl(bb));
        System.out.println(getHttpsUrl(cc));
    }
}
