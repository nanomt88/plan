package com.siyue.solar.solarsystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanomt88@gmail.com
 * @create 2020-02-18 12:59
 **/
public class Test {

    public static void main(String[] args) {
        String json = "{\"key\" : [\"a\", \"ab\", \"abc\"], \"key2\" : \"a2\"}";

        Map<String, List<String>> ppp = new HashMap<>();
        Map<String, Object> map = JSON.parseObject(json, Map.class);
        map.forEach((key, value) -> {
            List<String> list = new ArrayList<>();
            if (value instanceof JSONArray) {
                JSONArray v = (JSONArray) value;

                for (Object obj : v) {
                    list.add(String.valueOf(obj));
                }
            }else if(value instanceof String){
                System.out.println(value);
                list.add((String) value);
            }

            ppp.put(key, list);
        });


        System.out.println(ppp);
    }
}
