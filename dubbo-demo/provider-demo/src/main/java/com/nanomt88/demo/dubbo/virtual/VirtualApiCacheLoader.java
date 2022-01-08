package com.nanomt88.demo.dubbo.virtual;

import com.nanomt88.demo.dubbo.DubboProviderClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
@Slf4j
public class VirtualApiCacheLoader implements ICacheLoader,InitializingBean, DisposableBean, ApplicationContextAware {

    private Map<String,VirtualDubboApiInfo> bean_cache = new ConcurrentHashMap<>();

    private static ApplicationContext APPLICATION_CONTEXT ;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("loading all beans...");
        loadingVirtualApiInfo();
        registServiceProvider();
    }

    @Override
    public void destroy() throws Exception {
        DubboProviderClientFactory providerClientFactory = DubboProviderClientFactory.getInstance();
        bean_cache.forEach((key, value) ->{
            providerClientFactory.unRegisterReferenceConfig(value);
        });
    }

    public VirtualDubboApiInfo getVirtualDubboApiInfo(String apiCode){
        return bean_cache.get(apiCode);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    private void registServiceProvider() {
        if(CollectionUtils.isEmpty(bean_cache)){
            return;
        }
        DubboProviderClientFactory providerClientFactory = DubboProviderClientFactory.getInstance();
        bean_cache.forEach((key, value) ->{
            providerClientFactory.registerReferenceConfig(value);
        });

    }

    private void loadingVirtualApiInfo(){
        String[] beanNames = APPLICATION_CONTEXT.getBeanDefinitionNames();
        for(String beanName : beanNames){
            Object bean = APPLICATION_CONTEXT.getBean(beanName);
            Class<?> beanClass = bean.getClass();
            Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(beanClass);
            for(Method method : allDeclaredMethods){
                VirtualDubboApi declaredAnnotation = method.getAnnotation(VirtualDubboApi.class);
                if(declaredAnnotation == null){
                    log.debug("跳过：[{}.{}()]", beanClass , method.getName());
                    continue;
                }
                VirtualDubboApiInfo apiInfo = new VirtualDubboApiInfo();
                apiInfo.setApiCode(declaredAnnotation.value());
                apiInfo.setBean(bean);
                apiInfo.setTargetMethod(method);
                apiInfo.setTargetClass(beanClass);
                apiInfo.setTargetInterface(getInterface(beanClass));
                apiInfo.setParameterTypes(method.getParameterTypes());
                bean_cache.put(declaredAnnotation.value(), apiInfo);
                log.info("加载自定义虚拟接口：[{}]", apiInfo);
            }
        }
    }

    private Class<?> getInterface(Class<?> beanClass){
        Class<?>[] interfaces = beanClass.getInterfaces();
        if(interfaces == null || interfaces.length < 1){
            return beanClass;
        }
        if(interfaces.length == 1){
            return interfaces[0];
        }
        if(interfaces.length > 1){
            throw new IllegalStateException("多个接口暂时不支持");
        }
        return beanClass;
    }
}
