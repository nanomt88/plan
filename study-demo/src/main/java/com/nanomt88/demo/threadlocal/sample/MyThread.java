package com.nanomt88.demo.threadlocal.sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author nanomt88@gmail.com
 * @create 2022/3/6 9:13
 **/
public class MyThread extends Thread{

    MyThreadLocal.MyThreadLocalMap mapDemo;

    private static final Map<Thread,MyThread> CURRENT_THREAD = new ConcurrentHashMap<>();

    private static int thread_run_time = 3;

    public static void main(String[] args) throws Exception {
        //案例1：测试自己写的功能是否正常
//        asThreadLocalTest();
        //案例2：测试修改后ThreadLocal是否内存泄露
//        oomTest();
    }

    /**
     * 测试自己写的 MyThreadLocal 功能是不是对的
     *
     * 运行之前需要设置：
     *      修改  thread_run_time =3 ，控制循环次数
     *
     */
    public static void asThreadLocalTest(){
        thread_run_time = 3;
        for(int i =0 ; i < 10; i ++ ){
            MyThread thread = new MyThread();
            thread.start();
        }
        toSleep(3000);
    }

    /**
     * 1. 若要复现oom，则要注销 MyThreadLocal.finalize()方法
     *
     * 2. 正常执行则可以要看到gc过程中，回收了 MyThreadLocal 和 value对象，需要通过jvm jvisualvm工具触发gc
     *
     *
     * 运行之前需要设置：
     *      设置  jvm内存大小   -Xmx256m
     */
    public static void oomTest(){
        thread_run_time = 1000;
        for(int i =0 ; i < 200; i ++ ){
            MyThread thread = new MyThread();
            thread.start();
        }
        toSleep(5500);
        System.out.println("GC start");
        System.gc();
        toSleep(5000);
    }


    /**
     * 模拟Thread获取当前线程对象
     * @return
     */
    public static MyThread currentThread(){
        Thread thread = Thread.currentThread();
        MyThread myThread1 = CURRENT_THREAD.get(thread);
        if(myThread1 == null){
            //double check
            synchronized (MyThread.class) {
                if(CURRENT_THREAD.get(thread) == null) {
                    MyThread myThread = new MyThread();
                    CURRENT_THREAD.put(thread, myThread);
                }
            }
        }
        return CURRENT_THREAD.get(thread);
    }

    @Override
    public void run() {
        int i = 0;
        //模拟 正常使用中 工作线程工作内容，要模拟内存泄露则要每次都 new MyThreadLocal()，
        // ThreadLocal是弱类型，触发gc后就可以回收，但是value因为被 Thread.ThreadLocalMap对象强应用，所以才会发生内存泄露
        for(; i < thread_run_time; i++){
            String name = Thread.currentThread().getName() + "-" + i;
            MyThreadLocal<User> local = new MyThreadLocal(name);
            //一个User对象占用 10K+内存
            User user = new User(name, 1024*10);
            local.set(user);
            toSleep(1000);
//            System.out.println("value : " + local.get().name);
        }
        System.out.println(MyThread.currentThread().mapDemo.toString());
    }

    static void toSleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 工具类， buffer占用比较大的内存
     */
    class User{
        String name ;
        byte[] buffer;
        public User(String name, int length) {
            this.name = name;
            this.buffer = new byte[length];
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
