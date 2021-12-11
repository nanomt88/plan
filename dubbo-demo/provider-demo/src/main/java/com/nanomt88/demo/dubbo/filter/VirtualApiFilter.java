package com.nanomt88.demo.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.dubbo.SpringBeanContextUtils;
import com.nanomt88.demo.dubbo.virtual.ICacheLoader;
import com.nanomt88.demo.dubbo.virtual.VirtualDubboApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * 自定义dubbo filter，作用是位自定义生成 ServerConfig接口提供路由
 *   自定义filter生效有两种方式： xml配置和注解
 *   xml配置中，给需要使用filter的service配置就可以
 *   注解配置如下，默认应该是全局
 */
//@Activate (group = "provider", order = 9999)
@Slf4j
//@Component
public class VirtualApiFilter implements Filter {


    ICacheLoader cacheLoader;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //前置调用
        log.info("my filter invoke ... ");
        Result result = null;
//        invoker.getInterface();
//        invocation.getMethodName();

        String apiCode = (String) invocation.getAttachment("apiCode");
        init();
        if(StringUtils.isEmpty(apiCode) ){
            log.info("没有配置apiCode，使用原生的调用");
            return invoker.invoke(invocation);
        }
        VirtualDubboApiInfo virtualDubboApiInfo = cacheLoader.getVirtualDubboApiInfo(apiCode);
        if(virtualDubboApiInfo == null){
            log.info("没有配置apiCode，使用原生的调用");
            return invoker.invoke(invocation);
            //throw new RuntimeException("没有apiCode");
        }
        Class<?> targetClass = virtualDubboApiInfo.getTargetInterface();
        Object targetBean = SpringBeanContextUtils.getBean(targetClass);
        Object[] arguments = invocation.getArguments();
        Class<?>[] parameterTypes = virtualDubboApiInfo.getTargetMethod().getParameterTypes();
        String methodName = virtualDubboApiInfo.getTargetMethod().getName();
        Method method = ReflectionUtils.findMethod(targetClass, methodName, parameterTypes);
        if(method == null){
            log.error("不存在: [{}.{},{}]方法", targetClass, methodName, parameterTypes);
            return invoker.invoke(invocation);
        }

        //如果直接调用则是下面invoke进行调用
//        Result invoke = invoker.invoke(invocation);
//
        Object returnObj = ReflectionUtils.invokeMethod(method, targetBean, arguments);
        if(returnObj != null){
            //自己实例化一个对象返回
            result = AsyncRpcResult.newDefaultAsyncResult(returnObj,null, invocation);
//             result = new AsyncRpcResult(null,invocation);
            result.setValue(returnObj);
        }

        //后置处理
        log.info("my filter invoke result : [{}]", returnObj);
        return result;
    }


    public void init(){
        if(cacheLoader == null){
            cacheLoader = SpringBeanContextUtils.getBean(ICacheLoader.class);
        }
    }
}
