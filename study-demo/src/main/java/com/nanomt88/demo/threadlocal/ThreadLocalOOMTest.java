package com.nanomt88.demo.threadlocal;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 重现ThreadLocal重启      -Xmx256m
 *
 *  hreadLocal内存泄漏的根源是：由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key
 *  就会导致内存泄漏，而不是因为弱引用。
 *
 * @author nanomt88@gmail.com
 * @create 2022/3/5 14:50
 **/
public class ThreadLocalOOMTest {

    private static final int THREAD_LOOP_SIZE = 5000;
    private static final int MOCK_BID_DATA_LOOP_SIZE = 10000;


    public static void main(String[] args) {
        //oom测试用例
        threadLocalOOM();
        //模拟ThreadLocal原理
//        threadLocalDemo();
    }

    private static void threadLocalDemo() {
        //引用队列，当WeakReference对象被回收时，会进入该队列，类型为：MyWeakEntry
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        //模拟ThreadLocal中的Map table对象，里面存储的是 ThreadLocal-key：value，数组结构
        MyWeakEntry[] entries = new MyWeakEntry[10];

        String key = new String("我是threadLocal Key");
        String value = "我是vaule";
        //将对象放入模拟的threadLocalMap  table对象中
        entries[3] = new MyWeakEntry(key, value, queue);
        //取消其key应用，目前没有指向key的应用
        key = null;
        new Thread(() -> {

            Object reference;
            System.out.println("开始监听");
            try {
                //判断对象中是否有该key，有说明已经被回收了
                while ((reference = queue.remove()) != null) {
                    MyWeakEntry myWeakEntry = (MyWeakEntry) reference;
                    String threadLocalKey = myWeakEntry.get();
                    System.out.println(reference + " : 被回收了");
                    System.out.println(threadLocalKey + " : 被回收了");
                    System.out.println(reference + " 对应的value ： " + entries[3].value);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("结束监听");
            }
        }).start();
        sleep(2000);
        System.gc();
        System.out.println("程序结束");
    }

    private static void threadLocalOOM() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < THREAD_LOOP_SIZE; i++) {
            int finalI = i;
            executorService.execute(() -> {
                //ThreadLocal底层是通过ThreadLocalMap来实现的，每个Thread对象（注意不是ThreadLocal对 象）中都存在⼀个ThreadLocalMap，
                // Map的key为ThreadLocal对象，Map的value为需要缓存的 值
                //所以此处ThreadLocal要写在里面，每次ThreadLocal new完了之后就会被回收，但是Value不会被回收
                ThreadLocal<List<User>> threadLocal = new ThreadLocal<List<User>>();
                threadLocal.set(new ThreadLocalOOMTest().addBigList());
                System.out.println(finalI + ":" + Thread.currentThread().getName());
//                threadLocal.remove();
            });
            sleep(100L);
        }
//        executorService.shutdown();
    }

    private List<User> addBigList() {
        List<User> params = new ArrayList<>(MOCK_BID_DATA_LOOP_SIZE);
        for (int i = 0; i < MOCK_BID_DATA_LOOP_SIZE; i++) {
            params.add(new User("大张伟", "password" + i, "男", i));
        }
        return params;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyWeakEntry extends WeakReference<String> {
        String value;

        /**
         * 在对象被回收后，会把弱引用对象，也就是WeakReference对象或者其子类的对象，
         * 放入队列ReferenceQueue中，注意不是被弱引用的对象，被弱引用的对象已经被回收了。
         *
         * @param thread
         * @param q
         */
        public MyWeakEntry(String thread, String value, ReferenceQueue<? super String> q) {
            super(thread, q);
            this.value = value;
        }
    }

    class User {
        private String userName;
        private String password;
        private String sex;
        private int age;

        public User() {
        }

        public User(String userName, String password, String sex, int age) {
            this.userName = userName;
            this.password = password;
            this.sex = sex;
            this.age = age;
        }

        /**
         * 覆盖父类finalize方法，该方法会在对象被回收时执行
         *
         * @throws Throwable
         */
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("User:" + userName + " finalize.");
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    "}, hashCode:" + this.hashCode();
        }
    }


    @Test
    public void testAnd() {
        AtomicInteger code = new AtomicInteger();
        int size = 16;
        int HASH_INCREMENT = 0x61c88647;
        System.out.println("0x61c88647 = " + HASH_INCREMENT);
        for (int i = 0; i < 100; i++) {
            code.addAndGet(HASH_INCREMENT);
            int curr = code.get();
            System.out.println(curr + " & " + (size - 1) + " = " + (curr & (size - 1)));
        }
    }
}
