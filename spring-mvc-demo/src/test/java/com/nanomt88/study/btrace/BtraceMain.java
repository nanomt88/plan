package com.nanomt88.study.btrace;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;


@BTrace
public class BtraceMain {


    // @OnMethod(clazz="com.nanomt88.study.springmvcdemo.controller.BtraceController",
    //      method="test")
    // public static void onThreadStart() {
    //     println("thread start!");
    // }

    //下例打印所有用时超过300毫秒的方法调用
    @OnMethod(clazz ="/com\\.nanomt88\\..+/", method ="/.+/", location = @Location(Kind.RETURN))
    public static void onDoFilter2(@ProbeClassName String pcn, @Duration long duration) {
        // 1000000 = 1毫秒
        long cost = duration/(1000000*1);
        if(cost > 1) {
            //System.out.println();
            print(strcat(strcat(strcat(strcat(name(probeClass()),"."), probeMethod()),":"), str(probeLine())));
            print("[");
            print(strcat("Time cost (*1ms): ", str(cost)));
            println("]");
        }
    }

}