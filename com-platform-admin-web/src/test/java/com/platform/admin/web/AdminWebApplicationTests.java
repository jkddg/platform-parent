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
        try {
            Object obj = engine.eval("eval(function (p, a, c, k, e, d) {\n" +
                    "    e = function (c) {\n" +
                    "        return (c < a ? '' : e(parseInt(c / a))) + ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36))\n" +
                    "    };\n" +
                    "    if (!''.replace(/^/, String)) {\n" +
                    "        while (c--) {\n" +
                    "            d[e(c)] = k[c] || e(c)\n" +
                    "        }\n" +
                    "        k = [function (e) {\n" +
                    "            return d[e]\n" +
                    "        }];\n" +
                    "        e = function () {\n" +
                    "            return '\\\\w+'\n" +
                    "        };\n" +
                    "        c = 1\n" +
                    "    }\n" +
                    "    ;\n" +
                    "    while (c--) {\n" +
                    "        if (k[c]) {\n" +
                    "            p = p.replace(new RegExp('\\\\b' + e(c) + '\\\\b', 'g'), k[c])\n" +
                    "        }\n" +
                    "    }\n" +
                    "    return p\n" +
                    "}('6 y(W){3 9,V=Q 28(\"(^| )\"+W+\"=([^;]*)(;|$)\");4(9=d.1A.1B(V)){k 1D(9[2])}z{k\\'\\'}}6 j(u,U,S,j){k u.T(0,U-1)+j+u.T(S,u.1I)}3 D=8.f.t;3 5=y(\"5\");4(5!=\\'\\'){5=1J(\"(\"+5+\")\");n=5.n;p=5.p;4(D.v(n+\"/\"+p)<0){R=j(D,26,1N,n+\"/\"+p);8.f.Z=R}}(6(){(6(i,s,o,g,r,a,m){i[\\'1w\\']=r;i[r]=i[r]||6(){(i[r].q=i[r].q||[]).1r(1u)},i[r].l=1*Q 1t();a=s.I(o),m=s.1v(o)[0];a.1O=1;a.F=g;m.1M.1Q(a,m)})(8,d,\\'2c\\',\\'//E.2d-P.7/P.29\\',\\'b\\');3 O=y(\\'1P\\');3 9=O.2b(\\'|\\');4(9[1]){b(\\'N\\',\\'H-M-1\\',\\'L\\',{\\'2a\\':9[1]})}z{b(\\'N\\',\\'H-M-1\\',\\'L\\')}3 w=K;3 J=K;6 x(){4(w)k;w=1R;4(J){3 h=d.I(\\'27\\');h.F=1k;h.25.24=\\'23\\';d.Y.22(h);8.1i(6(){d.Y.21(h);8.f.t=c},G)}z{8.f.t=c}}b(\\'20\\',\\'1Z\\',f.t);b(\\'1h\\',\\'1Y\\');c=\\'B://1l.C.7/1o/1p?e=1f%1e%1d%1b%1a%19%15%13%12&1g=10&11=14%16&17=18&1c=1q&1n=1m\\';3 A=1X.1W.1V();4(A.v(\\'X/1U\\')>0||A.v(\\'X/1S\\')>0){c=c.Z(\\'B://E.1j.7\\',\\'1T://E.1j.7\\')}1k=\\'B://1l.C.7/1o/1p?e=1f%1e%1d%1b%1a%19%15%13%12&1g=10&11=14%16&17=18&1c=1q&1n=1m\\';b(\\'1h\\',\\'1s\\',\\'直达链接\\',\\'1y\\',\\'1H\\',{\\'1x\\':\\'1L\\',\\'1K\\':\\'1G\\',\\'1z\\':\\'1F\\',\\'1E\\':\\'C.7\\',\\'1C\\':x});1i(x,G)})()', 62, 138, '|||var|if|zdm_track_info|function|com|window|arr||ga|smzdmhref|document||location||ifr||changeStr|return|||source||channel||||href|allstr|indexOf|redirected|redirect|getCookie|else|uaStr|https|taobao|this_url|www|src|1000|UA|createElement|is_amazon|false|auto|27058866|create|cookie_user|analytics|new|go_url|end|substring|start|reg|name|chrome|body|replace|0b0bafb815675125860788330e|union_lens|3D|2FU|lensId|2Fdk|3A0b093c83_0c61_16cf707e774_e86b|xId|WCHMsB45CeE20CTLMjR8tA5CVs2iOyYW3lxYGQmzKhw0shQynt3miIXQTr8NoJwmCJl23cLWpuMDIlj8AKwJsy|2FDATJnbK5InWznd4dRbTb5WN9VqM6BWlz381n7MM6p1XL5HTZI7gbiaB41NehzSgBVenFkPJ9|2Fdxq|2FWOnOzLnr2jp926RHPMN4jYDACKRRghcpA3BvVwMOMHVq|activityId|2Bngv9PVQ2yckYWkamxjBDUoNzdC6dt479FzBO0OPao0BnOv|2F|tT7fJDsBQKgGQASttHIRqQ6m35Nv|traceId|send|setTimeout.|linkstars|smzdmhref1|uland|mm_25282911_3455987_122436732|pid|coupon|edetail|1d373dae54fd4b8aa1c9613e273adc14|push|event|Date|arguments|getElementsByTagName|GoogleAnalyticsObject|dimension29|cb|dimension1|cookie|match|hitCallback|unescape|dimension30|10724|37|yh|length|eval|dimension6|aa|parentNode|30|async|user|insertBefore|true|54|http|53|toLowerCase|userAgent|navigator|pageview|page|set|removeChild|appendChild|none|display|style||iframe|RegExp|js|userId|split|script|google'.split('|'), 0, {}))");
            System.out.println("obj=" + obj);

        } catch (Exception e) {
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
            Object result = engine.eval(fileReader);
            System.out.println(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
