package com.nanomt88.demo.dubbo.generic;

import org.apache.dubbo.config.ConsumerConfig;
import com.nanomt88.demo.dubbo.SpringBeanContextUtils;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最简单的泛化调用的例子
 */
@Slf4j
@Service
public class MyGenericServiceImpl implements IGenericService {

    private Map<String, ReferenceConfig> map = new ConcurrentHashMap<>();

    @Override
    public void  invokeUser(User user)throws GenericException {
        ReferenceConfig<GenericService> reference = map.get("com.nanomt88.demo.dubbo.sample.IMyService");
        if(reference == null) {
            ApplicationConfig applicationName = SpringBeanContextUtils.getBean("applicationName");
            Object registryConfig = SpringBeanContextUtils.getBean("dubboRegistry");

            // 引用远程服务
            // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
            reference = new ReferenceConfig<>();
            reference.setApplication(applicationName);
            reference.setRegistry((RegistryConfig) registryConfig);
            // 弱类型接口名
            reference.setInterface("com.nanomt88.demo.dubbo.sample.IMyService");
            reference.setVersion("1.0.0");
            // 声明为泛化接口
            reference.setGeneric("true");

            map.put(reference.getInterface(), reference);
        }
        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();
        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"张三"});
//        Object result = genericService.$invoke("sayHello", new String[] {"com.nanomt88.demo.dubbo.sample.User"}, new Object[] {user});
        log.info("返回结果：[{}]" ,result);

    }

}