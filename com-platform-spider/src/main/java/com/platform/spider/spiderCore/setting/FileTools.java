package com.platform.spider.spiderCore.setting;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FileTools {
    public static String readToString(InputStream inputStream) {
        String encoding = "UTF-8";
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        try {
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {

                sb.append(new String(b, 0, len, encoding));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return sb.toString();
    }
}
