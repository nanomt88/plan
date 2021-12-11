package com.nanomt88.demo.dubbo.async;

import com.nanomt88.demo.dubbo.sample.Order;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class AsyncTaskManager {

    private static Map<String,AsyncTaskData<Order>> concurrentMap = new ConcurrentHashMap<>();

    //todo 需要换成链表，且可以快速查询的
//    private static ConcurrentSkipListSet<String> linkedList = new ConcurrentSkipListSet();

    public static boolean containsKey(String requestNo){
        return concurrentMap.containsKey(requestNo);
    }

    public static void notify(String requestNo, Order order){
        if(!concurrentMap.containsKey(requestNo)){
            log.warn("不存在该交易：[{}]", requestNo);
            return;
        }
        //获取线程中的数据
        AsyncTaskData lock = concurrentMap.get(requestNo);
        if(lock != null) {
            synchronized (lock) {
                lock.setData(order);
                log.info("唤醒等待请求 requestNo:{}", requestNo);
                lock.notifyAll();
                //这里不能删除缓存中的数据，删除之后就存在问题，需要异步去检查数据
            }
            lock = null;
        }
    }

    /**
     * 等待 10秒
     * @param requestNo
     * @param callback
     */
    public static Order wait10s(String requestNo, Callable callback){
        if(concurrentMap.containsKey(requestNo)){
            log.warn("不存在该交易：[{}]", requestNo);
            return null;
        }
        AsyncTaskData<Order> lock = new AsyncTaskData(System.currentTimeMillis(),null);
        AsyncTaskData success = concurrentMap.putIfAbsent(requestNo, lock);
        if(success != null){
            //放失败了，返回
            log.error("重复交易：[{}]", requestNo);
            return null;
        }
        //放置成功了，调用回调函数；可以使用异步处理
        if(callback != null){
            try {
                callback.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long start = System.currentTimeMillis();
        synchronized (lock) {
//            linkedList.add(requestNo);
            try {
                lock.wait(10000);
            } catch (InterruptedException e) {
                log.error("中断异常 ", e);
            } catch (Exception e) {
                log.error("requestNo：[{}] 其他异常,[{}] ", requestNo, e);
            }
            long end = System.currentTimeMillis();
            if((end - start) > 10000) {
                log.warn("该请求requestNo:[{}] 超时了...",requestNo);
            }
            log.info("requestNo:{}等待结果耗时：{}ms", requestNo, (end - start));
            concurrentMap.remove(requestNo);
            return lock.getData();
        }
    }

    //todo 需要添加timer 定时清除
//    class TimeoutCleanerThread extends TimerTask {
//
//        @Override
//        public void run() {
//
//            while (true){
//                LockSupport.parkNanos(1000);
//                String requestNo = linkedList.first();
//                if(!concurrentMap.containsKey(requestNo)){
//                    linkedList.pollFirst();
//                }
//                AsyncTaskData<Order> orderAsyncTaskData = concurrentMap.get(requestNo);
//                long start = orderAsyncTaskData.getStart();
//                if( (System.currentTimeMillis()-start) > 10000){
//                    concurrentMap.remove(requestNo);
//                    linkedList.pollFirst();
//                }
//            }
//        }
//    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        String requestNo = "ABC123";
        new Thread( () -> {
            for(int i=0; i< 10; i++) {
                Order order = wait10s(requestNo + i, () -> {
                    log.info("回调函数");
                    return true;
                });
                System.out.println("当前order："+ order.getId());
            }
            countDownLatch.countDown();
        }).start();
        Thread.sleep(100);
        new Thread( () -> {
            for(int i=0; i< 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notify(requestNo+i, new Order(""+i));
            }
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
    }
}
