package com.platform.spider.spiderCore.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.spider.spiderCore.Common;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Settings {
    public List<String> pipelines;
    private List<String> middlewares;

    public double download_timeout = 180;
    public double download_delay = 0;
    public boolean retry_enabled = false;
    public int retry_times = 0;
    public int retry_counts = 0;
    public int concurrent_requests = 0;
    public String[] handle_httpstatus_list;

    private String customSettings;
    private String settings;

    public Settings(String customSettingString) throws RuntimeException {
        this.settings = FileTools.readToString(this.getClass().getResourceAsStream("/settings.json"));
        this.customSettings = customSettingString;

        this.pipelines = new ArrayList<>();
        this.middlewares = new ArrayList<>();

        this.parse(this.settings, this.customSettings);
    }

    private void parse(String message, String customString) throws RuntimeException {
        JSONObject jsonObject = JSON.parseObject(message);
        JSONObject customObject = JSON.parseObject(customString);

        if (jsonObject.keySet().contains("ITEM_PIPELINES")) {
            JSONObject object = (JSONObject) jsonObject.get("ITEM_PIPELINES");

            List<Integer> temp = new ArrayList<>();
            Map<Integer, String> tempMap = new HashMap<>();
            for (String key : object.keySet()) {
                if (!temp.contains((Integer) object.get(key))) {
                    temp.add((Integer) object.get(key));
                    tempMap.put((Integer) object.get(key), key);
                } else {
                    throw new RuntimeException("ITEM_PIPELINES values,It must be different");
                }
            }

            temp.sort(Common.sortDesc);

            for (Integer i : temp) {
                this.pipelines.add(tempMap.get(i));
            }
        }

        if (jsonObject.keySet().contains("DOWNLOADER_MIDDLEWARES")) {
            JSONObject object = (JSONObject) jsonObject.get("DOWNLOADER_MIDDLEWARES");

            List<Integer> temp = new ArrayList<>();
            Map<Integer, String> tempMap = new HashMap<>();
            for (String key : object.keySet()) {
                if (!temp.contains((Integer) object.get(key))) {
                    temp.add((Integer) object.get(key));
                    tempMap.put((Integer) object.get(key), key);
                } else {
                    throw new RuntimeException("DOWNLOADER_MIDDLEWARES values,It must be different");
                }
            }

            temp.sort(Common.sortDesc);

            for (Integer i : temp) {
                this.middlewares.add(tempMap.get(i));
            }
        }

        this.download_timeout = this.getDefault(jsonObject, customObject,
                "DOWNLOAD_TIMEOUT", SettingDefault.DOWNLOAD_TIMEOUT);
        this.download_delay = this.getDefault(jsonObject, customObject,
                "DOWNLOAD_DELAY", SettingDefault.DOWNLOAD_DELAY);
        this.retry_enabled = this.getDefault(jsonObject, customObject,
                "RETRY_ENABLED", SettingDefault.RETRY_ENABLED);
        this.retry_times = this.getDefault(jsonObject, customObject,
                "RETRY_TIMES", SettingDefault.RETRY_TIMES);
        this.concurrent_requests = this.getDefault(jsonObject, customObject,
                "CONCURRENT_REQUESTS", SettingDefault.CONCURRENT_REQUESTS);
        this.handle_httpstatus_list = this.getDefault(jsonObject, customObject,
                "HANDLE_HTTPSTATUS_LIST", SettingDefault.HANDLE_HTTPSTATUS_LIST);
        this.retry_counts = this.getDefault(jsonObject, customObject,
                "RETRY_COUNTS", SettingDefault.RETRY_COUNTS);
    }

    private String[] getDefault(JSONObject object1, JSONObject object2, String key, String[] defaultValue) {
        String[] result;
        if (object2!=null && object2.keySet().contains(key)) {
            JSONArray temp = object2.getJSONArray(key);
            String[] temps = new String[temp.size()];
            for (int i = 0; i < temps.length; i++) {
                temps[i] = "" + temp.get(i);
            }
            return temps;
        }
        if (object1!=null && object1.keySet().contains(key)) {
            JSONArray temp = object1.getJSONArray(key);
            String[] temps = new String[temp.size()];
            for (int i = 0; i < temps.length; i++) {
                temps[i] = "" + temp.get(i);
            }
            return temps;
        } else {
            result = defaultValue;
        }
        return result;
    }

    private boolean getDefault(JSONObject object1, JSONObject object2, String key, boolean defaultValue) {
        boolean result = false;
        if (object2 != null && object2.keySet().contains(key)) {
            return object1.getBoolean(key);
        }
        if (object1 != null && object1.keySet().contains(key)) {
            return object1.getBoolean(key);
        } else {
            result = defaultValue;
        }
        return result;
    }

    private int getDefault(JSONObject object1, JSONObject object2, String key, int defaultValue) {
        int result;
        if (object2 != null && object2.keySet().contains(key)) {
            int temp = object1.getInteger(key);
            if (temp > 0) {
                return temp;
            }
        }
        if (object1 != null && object1.keySet().contains(key)) {
            int temp = object1.getInteger(key);
            if (temp > 0) {
                result = temp;
            } else {
                result = defaultValue;
            }
        } else {
            result = defaultValue;
        }
        return result;
    }

    private double getDefault(JSONObject object1, JSONObject object2, String key, double defaultValue) {
        double result;
        if (object2 != null && object2.keySet().contains(key)) {
            double temp = object1.getDouble(key);
            if (temp > 0) {
                return temp;
            }
        }
        if (object1 != null && object1.keySet().contains(key)) {
            double temp = object1.getDouble(key);
            if (temp > 0) {
                result = temp;
            } else {
                result = defaultValue;
            }
        } else {
            result = defaultValue;
        }
        return result;
    }

    public List<String> getPipelines() {
        return pipelines;
    }

    public List<String> getMiddlewares() {
        return middlewares;
    }
}
