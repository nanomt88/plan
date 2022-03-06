package com.nanomt88.demo.threadlocal.sample;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @author nanomt88@gmail.com
 * @create 2022/3/6 9:13
 **/
public class MyThreadLocal<T> {

    /**
     * 魔术值，用于计算hash值时均分分布
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * 当前对象的hashCode值
     */
    private final int threadLocalHashCode = nextHashCode();

    /**
     * 用于生成当前对象的 hashCode值
     */
    private static AtomicInteger HASH_CODE = new AtomicInteger(0);

    /**
     *  ****************** 重点 ********************
     *  这个对象与jdk中 Thread.ThreadLocalMap对象引用相同，为了能够通过MyThreadLocal对象找到value，需要将这个map缓存下来
     *  为什么jdk中不这么实现呢？？？ 不优雅？？？
     */
    MyThreadLocalMap mapDemo;
    /**
     * 打印名称用的
     */
    private String name ;

    public MyThreadLocal(){
        this.name = MyThread.currentThread().getName();
    }

    public MyThreadLocal(String name){
        this.name = name;
    }

    public void set(T value){
        //获取当前线程
        MyThread currentThread = MyThread.currentThread();
        MyThreadLocalMap map = getMap(currentThread);
        if(map == null){
            //创建map
            createMap(currentThread, value);
        }else {
            map.set(this, value);
        }
    }

    public T get(){
        MyThread currentThread = MyThread.currentThread();
        MyThreadLocalMap map = getMap(currentThread);
        if(map == null){
            //如果线程 MyThread没有初始化MyThreadLocalMap，则在此初始化和设置value
            return initialDefaultValue(currentThread);
        }
        MyThreadLocalMap.MyEntity entry = map.getEntry(this);
        if (entry != null) {
            return (T)entry.value;
        }
        return initialDefaultValue(currentThread);
    }

    /**
     * 删除 MyThreadLocal中存储的 value值
     */
    public void remove(){
        //通过线程获取到map，然后删除对象
        MyThread currentThread = MyThread.currentThread();
        MyThreadLocalMap map = getMap(currentThread);
        if(map != null){
            map.remove(this);
            return;
        }
        // ******************* 重点 *********************
        // 若是通过gc回收触发的，则currentThread对应的map为null，没有MyThreadLocalMap则无法找到value，无法释放value
        // 为了解决这个问题，则将MyThreadLocalMap对象也应用到 MyThreadLocal中，则可以找到value对象然后释放
        if(mapDemo != null){
//            System.out.println("remove " + this.get());
            mapDemo.remove(this);
        }

    }

    /**
     * 重写 gc垃圾回收方法，释放 MyThreadLocal对应的value
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
//        System.out.println( name + " finalize run...");
//        remove();
        // ******************* 重点 *********************
        // 若是通过gc回收触发的，则与工作线程不同，则MyThread.MyThreadLocalMap为null，没有MyThreadLocalMap则无法找到value，无法释放value
        // 为了解决这个问题，则将MyThreadLocalMap对象也应用到 MyThreadLocal中，则可以找到value对象然后释放
        if(mapDemo != null) {
            mapDemo.remove(this);
        }
    }

    /**
     * 初始化ThreadLocal值，未初始化则为null
     * @return
     */
    private T initialDefaultValue( MyThread currentThread) {
        //默认值为null
        T  defaultValue = null;
        MyThreadLocalMap map = getMap(currentThread);
        if(map == null){
            //如果线程 MyThread没有初始化MyThreadLocalMap，则在此初始化
            createMap(currentThread, defaultValue);
        }else {
            map.set(this, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 初始化 线程MyThread的 MyThreadLocalMap对象，用于保存线程 ThreadLocal的value
     * @param myThread
     * @param value
     */
    private void createMap(MyThread myThread, Object value) {
        myThread.mapDemo = new MyThreadLocalMap(this, value);
        MyThreadLocalMap demo = myThread.mapDemo;
    }


    MyThreadLocalMap getMap(MyThread myThread){
        return myThread.mapDemo;
    }

    /**
     * 计算对象的hash值
     * @return
     */
    public int hashCode() {
        return HASH_CODE.get();
    }

    /**
     * 计算对象的hash值
     * @return
     */
    private int nextHashCode() {
        return HASH_CODE.getAndAdd(HASH_INCREMENT);
    }

    /**
     * //todo 没有处理hash冲突，jdk中处理hash冲突很简单就是循环到下一个key
     */
    static class MyThreadLocalMap {

        static int INITIAL_CAPACITY = 128*16;
        MyEntity[] entities ;
        int size = 0;

        private MyThreadLocalMap(MyThreadLocal<?> firstKey, Object firstValue){
            //初始化map中的数据结构：数组
            entities = new MyEntity[INITIAL_CAPACITY];
            int index = getIndex(firstKey);
            entities[index] = new MyEntity(firstKey, firstValue);
            size = 1;
            // 为了能在 MyThreadLocal对象回收时，回收value添加的引用
            firstKey.mapDemo = this;
        }

        private void set(MyThreadLocal<?> key, Object value){
            int index = getIndex(key);
            MyEntity entity = entities[index];
            //todo hash冲突未解决
            if(entity != null && entity.get() == key){
                entity.value = value;
                return;
            }else if(entity != null && entity.get() != key){
                //这里存在hash冲突的问题
                entity.clear();
                entity.value = null;
                size--;
            }
            entities[index] = new MyEntity(key, value);
            size ++ ;
            // 为了能在 MyThreadLocal对象回收时，回收value添加的引用
            key.mapDemo = this;
        }

        private MyEntity getEntry(MyThreadLocal<?> key){
            int index = getIndex(key);
            MyEntity entity = entities[index];
            //todo hash冲突未解决, 这里存在hash冲突的问题
            if(entity != null && entity.get() == key){
                return entity;
            }
            return null;
        }

        /**
         * 删除 MyThreadL对应的value
         * @param key
         */
        private void remove(MyThreadLocal<?> key) {
            int index = getIndex(key);
            MyEntity entity = entities[index];
            if(entity != null) {
                System.out.println("remove : " + entity.value.toString());
                //释放 MyThreadLocal与 MyEntity的引用关键，gc则可以回收MyThreadLocal对象了
                entity.clear();
                //删除value与 MyEntity的关联关系，并且释放value；gc则可以回收value了
                entity.value = null;
                entities[index] = null;
                size --;
            }
        }

        /**
         * 通过MyThreadLocal的hashCode获取在数组中的位置
         * @param threadLocal
         * @return
         */
        private int getIndex(MyThreadLocal<?> threadLocal){
            int index = threadLocal.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            return index;
        }

        @Override
        public String toString() {
            return "MyThreadLocalMap{" +
                    "entities=" + Arrays.toString(entities) +
                    ", size=" + size +
                    '}';
        }

        static class MyEntity extends WeakReference<MyThreadLocal<?>> {

            private Object value;

            public MyEntity(MyThreadLocal<?> referent, Object value) {
                super(referent);
                this.value = value;
            }

            @Override
            public String toString() {
                return "MyEntity{" +
                        "key=" + super.toString() +
                        ", value=" + value +
                        '}';
            }
        }
    }
}
