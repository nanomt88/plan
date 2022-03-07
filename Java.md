---
typora-root-url: res
---

# Java技术点



### ThreadLocal原理

#### 总结

1. 一般使用ThreadLocal，官方建议定义为private static
2. ThreadLocal是Java中所提供的线程本地存储机制，可以利⽤该机制将数据缓存在某个线程内部，成为线程内的局部变量；该线程可以在任意时刻、任意⽅法中获取缓存的数据

**ThreadLocal  源码及原理 ** 

~~~java
class Thread implements Runnable {
    //ThreadLocal保存的数据数据map对象，放在当前线程中，防止多线程并发
    ThreadLocal.ThreadLocalMap threadLocals = null;
    //父子线程传递ThreadLocal
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
}

/**
	可以看到，map对象 entry继承了弱引用类
	在构造方法中，调用了super(k)将 key对象传给了父类
**/
public class ThreadLocal<T> {
	static class ThreadLocalMap {
		static class Entry extends WeakReference<ThreadLocal<?>> {
	            /**  当前线程Thread存储的ThreadLocal对应的value  */
	            Object value;
	            Entry(ThreadLocal<?> k, Object v) {
	                super(k);
	                value = v;
	            }
        }
	}
}
/**
	在其父类中，我们找到了想要找的 referent
**/
public class WeakReference<T> extends Reference<T> {
	public WeakReference(T referent) {
	       super(referent);
	   }
}
/**
	通过将referent加入 弱引用队列，保证下次gc时，referent能够被回收，因此便出现了 gc后 该值变为null的情况
**/
public abstract class Reference<T> {
    Reference(T referent) {
        this(referent, null);
    }
}

~~~

ThreadLocal内存对象关系图

![ThreadLocal_heap](/ThreadLocal_heap.png)

ThreadLoca在使用过程中，内存存储实例示意，其中正常使用时都将 ThreadLocal对象设置为某类的static变量。其中 某类的对象2 为弱应用

![threadlocal-instance](/threadlocal-instance.png)



ThreadLocal内存泄露原因 ：

* 工作线程长时间运行， 请求执行完成后未调用Threadl.remove()方法释放内存。ThreadLocal因为gc运行而被回收，但是 ThreadLocal对应的value 因为被Thread.ThreadLocalMap --> Entity.value对象 强应用，而导致 出现ThreadLocalMap的数组中，出现 null --> value 的数据无法被释放。

ThreadLocal 内存泄露示例：

> ThreadLocalOOMTest.threadLocalOOM() ，触发内存泄露OOM现象
>
> ThreadLocalOOMTest.threadLocalDemo() ，简单写一个ThreadLoca的数据接口，然后重现内存泄露现象

**ThreadLocal如何解决内存泄露**：在ThreadLocal.finalize()时，一并释放对应的value

> com.nanomt88.demo.threadlocal.sample.MyThread.oomTest ，解决ThreadLocal内存泄露方法入口
>
> com.nanomt88.demo.threadlocal.sample.MyThreadLocal，重写ThreadLocal类，增加finalize()方法，在gc回收ThreadLocal时，一并释放ThreadLocalMap.entity[]数据中，Entity所持有的value



其他彩蛋：

* 若数组的长度是2的次方时，解决hash计算并存到数组中有更加优雅的方法，使用斐波那契数列0x61c88647 可以轻松解决