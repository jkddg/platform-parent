package com.platform.admin.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminWebApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            engine.eval("function add(a,b){" +
                    "return a+b;" +
                    "}");
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                System.out.println(in.invokeFunction("add", 1, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
           Object obj = engine.eval("eval(function(p, a, c, k, e, d) {\n" +
                   "    e = function(c) {\n" +
                   "        return (c < a ? '': e(parseInt(c / a))) + ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36))\n" +
                   "    };\n" +
                   "    if (!''.replace(/^/, String)) {\n" +
                   "        while (c--) {\n" +
                   "            d[e(c)] = k[c] || e(c)\n" +
                   "        }\n" +
                   "        k = [function(e) {\n" +
                   "            return d[e]\n" +
                   "        }];\n" +
                   "        e = function() {\n" +
                   "            return '\\\\w+'\n" +
                   "        };\n" +
                   "        c = 1\n" +
                   "    };\n" +
                   "    while (c--) {\n" +
                   "        if (k[c]) {\n" +
                   "            p = p.replace(new RegExp('\\\\b' + e(c) + '\\\\b', 'g'), k[c])\n" +
                   "        }\n" +
                   "    }\n" +
                   "\treturn p;\n" +
                   "   \n" +
                   "} ('6 x(X){3 7,Y=I 23(\"(^| )\"+X+\"=([^;]*)(;|$)\");4(7=f.1l.1m(Y)){n 1o(7[2])}w{n\\'\\'}}6 u(t,Z,11,u){n t.10(0,Z-1)+u+t.10(11,t.1p)}3 B=9.c.p;3 5=x(\"5\");4(5!=\\'\\'){5=1r(\"(\"+5+\")\");k=5.k;j=5.j;4(B.C(k+\"/\"+j)<0){H=u(B,26,1A,k+\"/\"+j);9.c.U=H}}(6(){(6(i,s,o,g,r,a,m){i[\\'1t\\']=r;i[r]=i[r]||6(){(i[r].q=i[r].q||[]).1x(1z)},i[r].l=1*I 1y();a=s.K(o),m=s.1w(o)[0];a.1v=1;a.J=g;m.1u.1s(a,m)})(9,f,\\'1q\\',\\'//A.1n-R.8/R.1k\\',\\'b\\');3 T=x(\\'1C\\');3 7=T.1U(\\'|\\');4(7[1]){b(\\'Q\\',\\'P-O-1\\',\\'N\\',{\\'1Y\\':7[1]})}w{b(\\'Q\\',\\'P-O-1\\',\\'N\\')}3 v=M;3 L=M;6 D(){4(v)n;v=1Z;4(L){3 d=f.K(\\'20\\');d.J=1b;d.21.22=\\'1X\\';f.S.1B(d);9.W(6(){f.S.24(d);9.c.p=h},G)}w{9.c.p=h}}b(\\'25\\',\\'1W\\',c.p);b(\\'12\\',\\'1M\\');h=\\'E://1e.y.8/1g/1h?e=1j%1a%1f%1d&1c=1i&19=V%17&16=15&18=13\\';3 z=1V.1E.1F();4(z.C(\\'F/1G\\')>0||z.C(\\'F/1H\\')>0){h=h.U(\\'E://A.14.8\\',\\'1I://A.14.8\\')}1b=\\'E://1e.y.8/1g/1h?e=1j%1a%1f%1d&1c=1i&19=V%17&16=15&18=13\\';b(\\'12\\',\\'1J\\',\\'直达链接\\',\\'1K\\',\\'1D\\',{\\'1L\\':\\'1N\\',\\'1O\\':\\'1P\\',\\'1Q\\':\\'1R\\',\\'1S\\':\\'y.8\\',\\'1T\\':D});W(D,G)})', 62, 131, '|||var|if|zdm_track_info|function|arr|com|window||ga|location|ifr||document||smzdmhref||channel|source|||return||href||||allstr|changeStr|redirected|else|getCookie|taobao|uaStr|www|this_url|indexOf|redirect|https|chrome|1000|go_url|new|src|createElement|is_amazon|false|auto|27058866|UA|create|analytics|body|cookie_user|replace|lensId|setTimeout|name|reg|start|substring|end|send|mm_25282911_3455987_122436732|linkstars|yxULXRbEIrcxNHxTqNUpcqkcGVIHHR8aTqU6z3ZgpeSweHhCht1pTzBKmnzXrqcUiVAeHDEOVTUSCVobUtwsrg|xId|3A0b1540db_0bb7_16cf5484710_c787|pid|union_lens|2BXj|smzdmhref1|traceId|2BkHL3AEW|uland|2FMFT2WJ9tYYjgTQy7GhFDcL4e1ZeoJoguPxqJ7|coupon|edetail|0b0f6ad815674832504206376e|4xKGFCvrD7UGQASttHIRqdW1KEq3igCvad5R7UReGi6kamxjBDUoNzdC6dt479FzlGfwXtWmS6nVHlCHpjw9Pmjp926RHPMN4jYDACKRRghcpA3BvVwMOHYefz8NXcoYTJnbK5InWzloKXCUZd8BcHFf5RMoZqfAYwOD23XOnRF641g|js|cookie|match|google|unescape|length|script|eval|insertBefore|GoogleAnalyticsObject|parentNode|async|getElementsByTagName|push|Date|arguments|30|appendChild|user|yh|userAgent|toLowerCase|53|54|http|event|cb|dimension29|pageview|aa|dimension6|95|dimension1|10947|dimension30|hitCallback|split|navigator|page|none|userId|true|iframe|style|display|RegExp|removeChild|set|'.split('|'), 0, {}))");
            System.out.println("obj="+obj);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String jsName = "https://go.smzdm.com/e3fc884265d30398/ca_aa_yh_147_15923669_11256_3857_151_322337";
        try {
            //读取js
            FileReader fileReader = new FileReader(jsName);
            //执行指定脚本
            Object result= engine.eval(fileReader);
            System.out.println(result);
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }

}
