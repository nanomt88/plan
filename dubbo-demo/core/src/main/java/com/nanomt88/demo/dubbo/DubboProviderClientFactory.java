package com.nanomt88.demo.dubbo;

import com.nanomt88.demo.dubbo.virtual.VirtualDubboApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * dubbo 动态服务注册客户端
 *
 * @author nanomt88@gmail.com
 * @create 2021-11-28 10:55
 **/
@Slf4j
public class DubboProviderClientFactory {

    private static String ZK_ADDRESS = "zookeeper://127.0.0.1:2181";

    private RegistryConfig registryConfig;
    private ApplicationConfig applicationConfig;

    private String version = "1.0.0";

    private static Map<String, ServiceConfig> REFERENCE_CACHE = new ConcurrentHashMap<>();

    private static class DubboServcieClientSingletonHloder {
        private static DubboProviderClientFactory INSTANCE = new DubboProviderClientFactory();
    }

    public static DubboProviderClientFactory getInstance() {
        return DubboServcieClientSingletonHloder.INSTANCE;
    }

    private DubboProviderClientFactory() {

        ApplicationConfig applicationConfig = SpringBeanContextUtils.getBean("applicationName");
        if (applicationConfig == null) {
            applicationConfig = new ApplicationConfig();
            //配置应用的信息
            applicationConfig.setName("provider-demo-generic");
            applicationConfig.setVersion(version);
        }

        //配置注册中心
        RegistryConfig registryConfig = SpringBeanContextUtils.getBean("dubboRegistry");
        if (registryConfig == null) {
            registryConfig = new RegistryConfig();
            registryConfig.setAddress(ZK_ADDRESS);
            registryConfig.setFile("/tmp/dubbo.cache");
        }
        this.applicationConfig = applicationConfig;
        this.registryConfig = registryConfig;
    }

    public void registerReferenceConfig(VirtualDubboApiInfo virtualDubboApiInfo) {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        //TODO fixme 这里应该取dubbo provider的端口，动态配置
        protocolConfig.setPort(1888);

        ServiceConfig config  = new ServiceConfig();
        config.setApplication(applicationConfig);
        config.setRegistry(registryConfig);
        config.setProtocol(protocolConfig);

        config.setInterface(virtualDubboApiInfo.getTargetInterface());
        config.setGeneric("true");
//        config.setVersion(version);
        config.setId(virtualDubboApiInfo.getApiCode());
        config.setRef(virtualDubboApiInfo.getBean());

        config.export();

        REFERENCE_CACHE.put(virtualDubboApiInfo.getApiCode(), config);
        log.info("注册服务成功：[{}]", virtualDubboApiInfo);
    }

    public void unRegisterReferenceConfig(VirtualDubboApiInfo virtualDubboApiInfo){
        if(REFERENCE_CACHE.containsKey(virtualDubboApiInfo.getApiCode())){
            ServiceConfig serviceConfig = REFERENCE_CACHE.get(virtualDubboApiInfo.getApiCode());
            serviceConfig.unexport();
            REFERENCE_CACHE.remove(virtualDubboApiInfo.getApiCode());
            serviceConfig = null;
            log.info("卸载服务成功：[{}]", virtualDubboApiInfo);
        }
    }
}
