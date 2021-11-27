package com.nanomt88.demo.lock;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自动释放锁实现类
 *
 * @author nanomt88@gmail.com
 * @create 2020-11-29 10:10
 **/
public class AutoUnlockProxy implements Closeable {

    private Lock lock;

    public AutoUnlockProxy(Lock lock){
        this.lock = lock;
    }

    @Override
    public void close() throws IOException {
        lock.unlock();
        System.out.println("自动释放锁");
    }

    public void tryLock() throws InterruptedException {
        lock.tryLock();
    }

    public void tryLock(long time, TimeUnit unit) throws InterruptedException {
        lock.tryLock(time, unit);
    }


    public static void main(String[] args) {

        try(AutoUnlockProxy autoUnlock = new AutoUnlockProxy(new ReentrantLock())){
            autoUnlock.tryLock();
            System.out.println("获取到锁了");
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
