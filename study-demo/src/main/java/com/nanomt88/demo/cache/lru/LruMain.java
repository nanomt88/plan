package com.nanomt88.demo.cache.lru;

/**
 * @author nanomt88@gmail.com
 * @create 2020-11-28 17:50
 **/
public class LruMain {

    public static void main(String[] args) {

        Storage<String,String> lowCache = new Storage<String,String>() {
            @Override
            public String get(String key) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return key + "111";
            }
        };


        LruCache<String,String> cache = new LruCacheImpl(2, lowCache);

        String a,b,c,d;

        a = cache.get("a");
        System.out.println("a = " + a);

        b = cache.get("b");
        System.out.println("b = " + b);

        a = cache.get("a");
        System.out.println("a = " + a);

        c = cache.get("c");
        System.out.println("c = " + c);


        d = cache.get("d");
        System.out.println("d = " + d);

        a = cache.get("a");
        System.out.println("a = " + a);
    }
}
