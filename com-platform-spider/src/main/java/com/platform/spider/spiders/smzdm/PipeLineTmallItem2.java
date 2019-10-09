package com.platform.spider.spiders.smzdm;

import com.platform.spider.spiderCore.ResponsePipeLine;
import com.platform.spider.spiderCore.SpiderResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.util.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Huangyonghao on 2019/9/26 15:56.
 */
public class PipeLineTmallItem2 extends ResponsePipeLine {

    Pattern p = Pattern.compile("smzdmhref='(.+?)'");
    private String baseUrl="http://gateway.kouss.com/tbpub/itemClickExtract";

    @Override
    public void processResponse(SpiderResponse response) {
        Document doc = Jsoup.parse(response.getText());
        Element element = doc.selectFirst("script");
        String scriptText = element.childNode(0).toString();
        scriptText = scriptText.replace(scriptText.split("eval")[0], "");
        scriptText = scriptText.substring(4);
        String tbUrl = this.getTbUrl(scriptText, null);
        if (!StringUtils.isEmpty(tbUrl)) {
            new SpiderSmzdmTmallItem(baseUrl, new PipeLineTmallItem3());
        }
    }

    private String getTbUrl(String scriptText, String funcName) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        try {
            Object obj = engine.eval(scriptText);
            if (engine instanceof Invocable && !StringUtils.isEmpty(funcName)) {
                Invocable in = (Invocable) engine;
                Object res = in.invokeFunction(funcName);
                System.out.println(res);
            }
//            System.out.println("obj=" + obj);

            Matcher m = p.matcher(obj.toString());
            if (m.find()) {
                return m.group(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}