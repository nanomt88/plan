package com.nanomt88.demo.dubbo.virtual;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  //保留注解到运行时，需要动态获取
public @interface VirtualDubboApi {
    /**
     * 定义dubbo的虚拟接口名称
     * @return
     */
    String value();
}
