package com.nanomt88.freemark;

import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class FreemarkTest {

    public static Configuration cfg;

    static {
        cfg = new Configuration(new Version("2.3.23"));
    }

    @Test
    public void threadTest() throws InterruptedException {

        /*
         总结：   并发增加后，耗时增加不明显
                在10个并发下，单个模板的平均处理时间 0.4ms
                在25个并发下，单个模板的平均处理时间 0.9ms
                在50个并发下，单个模板的平均处理时间 1.8ms
                在100个并发下，单个模板的平均处理时间 3.8ms
                在250个并发下，单个模板的平均处理时间 3.8ms
                在500个并发下，单个模板的平均处理时间 28ms
         单个，总：38.373s， 平均：3.8373ms
         */
        for(int i =0 ; i< 1; i++) {
            Addr addr = Addr.builder().city("深圳"+i).street("街道2"+i).country("中国").build();
            List<Links> list = new ArrayList<>();
            Links links1 = Links.builder().name("知乎"+i).url("http://www.abc.com/").build();
            Links links2 = Links.builder().name("章鱼"+i).url("http://www.124.com/").build();
            Links links3 = Links.builder().name("土豆").url("http://www.345.com/"+i).build();
            Links links4 = Links.builder().name("优酷").url("http://www.ok.com/"+i).build();
            list.add(links1);list.add(links2);list.add(links3);list.add(links4);

            TestBean testBean = TestBean.builder().name("张三"+i)
                    .url("http://www.baidu.com/"+i).page(1).isNonProfit(true)
                    .address(addr).links(list).build();

            Map map = new Gson().fromJson(new Gson().toJson(testBean), Map.class);

            long start = System.currentTimeMillis();
            int j=0 ;
            String template2 = buildTemplate2();

            int count = 500;
            CountDownLatch countDownLatch = new CountDownLatch(count);
            final AtomicLong max = new AtomicLong(0);
            for(; j< count; j++) {
                new Thread(() -> {
                    long s1 = System.currentTimeMillis();
                    for(int k=0; k< 10000; k++) {
                        String result = processFreemarker(template2, map);
                    }
                    long e1 = System.currentTimeMillis();

                    System.out.printf("单个，总：%ss， 平均：%sms \n" ,(e1-s1)*1D/1000, (e1-s1)*1D/10000);
                    max.addAndGet(e1-s1);
                    countDownLatch.countDown();
                }).start();

            }
            countDownLatch.await();
            long end = System.currentTimeMillis();
            //总计：1355ms， 平均：0.1355ms
            System.out.printf("总计1：%ss， 平均：%sms \n" ,(end-start)*1D/1000, (end-start)*1D/j);
            System.out.printf("总计2：%ss， 平均：%sms \n" ,max.get()*1D/1000, max.get()*1D/j/1000);
        }
    }


    @Test
    public void freemarkTest() {


        String json = "{\n" +
                "    \"name\": \"BeJson\",\n" +
                "    \"url\": \"http://www.bejson.com\",\n" +
                "    \"page\": 88,\n" +
                "    \"isNonProfit\": true,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"科技园路.\",\n" +
                "        \"city\": \"江苏苏州\",\n" +
                "        \"country\": \"中国\"\n" +
                "    },\n" +
                "    \"links\": [\n" +
                "        {\n" +
                "            \"name\": \"Google\",\n" +
                "            \"url\": \"http://www.google.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Baidu\",\n" +
                "            \"url\": \"http://www.baidu.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"SoSo\",\n" +
                "            \"url\": \"http://www.SoSo.com\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Map map = new Gson().fromJson(json, Map.class);

        System.out.println(processFreemarker(buildTemplate(), map));
        System.out.println(processFreemarker(buildTemplate2(), map));
    }

    private static String buildTemplate(){
        String templae = "{\n" +
                "    \"name\": \"${name}\",\n" +
                "    \"url\": \"${url}\",\n" +
                "    \"page\": 88,\n" +
                "    \"isNonProfit\": true,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"${address.street}\",\n" +
                "        \"city\": \"江苏苏州\",\n" +
                "        \"country\": \"中国\"\n" +
                "    },\n" +
                "    \"links\": [\n" +
                "       <#list links as  link >\n" +
                "      \t   <#if link.name  == \"Baidu\" && link.url = \"http://www.baidu.com\" >\n" +
                "         \t{\n" +
                "                \"name\": \"${link.name}\",\n" +
                "          \t     \"url\": \"${link.url}\"\n" +
                "            }<#sep>,\t\t\t \n" +
                "     \t\t</#if>\n" +
                "       </#list>\n" +
                "]\n" +
                "}";
        return templae;
    }

    private static String buildTemplate2(){
        String temple = "{\n" +
                "    \"name2\": \"${name}\",\n" +
                "    \"url2\": \"${url}\",\n" +
                "    \"page2\": ${page},\n" +
                "    \"isNonProfit2\": ${isNonProfit?string('true','false')},\n" +
                "    \"address-new\": {\n" +
                "        \"street2\": \"${address.street}\",\n" +
                "        \"city2\": \"${address.city}\",\n" +
                "        \"country2\": \"${address.country}\"\n" +
                "    },\n" +
                "    \"links\": [\n" +
                "       <#list links as  link >\n" +
//                "      \t   <#if link.name  == \"Baidu\" && link.url = \"http://www.baidu.com\" >\n" +
                "         \t{\n" +
                "                \"name2\": \"${link.name}\",\n" +
                "          \t     \"url2\": \"${link.url}\"\n" +
                "            }<#sep>,\t\t\t \n" +
//                "     \t\t</#if>\n" +
                "       </#list>\n" +
                "]\n" +
                "}";
        return temple;
    }

    /**
     * Freemarker渲染模板
     *
     * @param template 模版
     * @param params   参数
     * @return
     */
    public static String processFreemarker(String template, Map<String, Object> params) {
        if (template == null || params == null)
            return null;
        try {
            StringWriter result = new StringWriter();
            Template tpl = new Template("strTpl", template, cfg);
            tpl.process(params, result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Freemarker渲染模板
     *
     * @param template 模版
     * @param params   参数
     * @return
     */
    public static String processFreemarker(String template, Object params) {
        if (template == null || params == null)
            return null;
        try {
            StringWriter result = new StringWriter();
            Template tpl = new Template("strTpl", template, cfg);
            tpl.process(params, result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

