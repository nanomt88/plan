package com.nanomt88.demo.dubbo.generic;

import com.nanomt88.demo.dubbo.SpringBeanContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

/**
 *  dubbo泛华调用客户端
 * @author nanomt88@gmail.com
 * @create 2021-11-28 10:55
 **/
@Slf4j
public class DubboServcieClientFactory {

    private RegistryConfig registryConfig;
    private ApplicationConfig applicationConfig;
    private String versoin = "1.0.0";

    private static class DubboServcieClientSingletonHloder{
        private static DubboServcieClientFactory INSTANCE = new DubboServcieClientFactory();
    }

    public static DubboServcieClientFactory getInstance(){
        return DubboServcieClientSingletonHloder.INSTANCE;
    }

    private DubboServcieClientFactory(){

        ApplicationConfig applicationConfig = SpringBeanContextUtils.getBean("applicationName");
        if(applicationConfig == null){
            applicationConfig = new ApplicationConfig();
            //配置应用的信息
            applicationConfig.setName("consumer-demo-generic");
            applicationConfig.setVersion("1.0.0");
        }

        //配置注册中心
        RegistryConfig registryConfig = SpringBeanContextUtils.getBean("dubboRegistry");
        if(registryConfig == null) {
            registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://127.0.0.1:2181");
            registryConfig.setFile("/tmp/dubbo.cache");
        }
        this.applicationConfig = applicationConfig;
        this.registryConfig = registryConfig;
    }

    public Object genericInvoke (String interfaceClass, String methodName, Object parameterValues){
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        // 弱类型接口名
        reference.setInterface(interfaceClass);
        reference.setVersion(versoin);
        reference.setGroup("dubbo");
        // 声明为泛化接口
        reference.setGeneric("true");

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        if(genericService == null){
            //因为ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，需要缓存，
            // 否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。所以在调用服务时，
            // 不论服务提供端接口服务是否存在，ReferenceConfigCache都会缓存ReferenceConfig实例
            // (若调用服务提供端不存在，ReferenceConfig实例的ref参数为空，即不能生成GenericService对象)，再下次请求时，即使服务提供端正常提供服务，但因为ReferenceConfigCache已经缓存了ReferenceConfig实例，所以不会再重新创建链接，直接从ReferenceConfigCach缓存中获取GenericService对象，导致GenericService实例一直报空指针(因为ref参数为空)，最终导致一直调用不到真正的服务
            cache.destroy(reference);
            throw new RuntimeException("服务不可用");
        }
        if(parameterValues == null){
            return  genericService.$invoke(interfaceClass, new String[] {}, new Object[] {});
        }
        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke(interfaceClass, new String[] {methodName}, new Object[] {parameterValues});
//        Object result = genericService.$invoke("sayHello", new String[] {"com.nanomt88.demo.dubbo.sample.User"}, new Object[] {user});
        log.info("返回结果：[{}]" ,result);
        return result;
    }
}
