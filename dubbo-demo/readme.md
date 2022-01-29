
## dubbo学习 
### 动态设置duubo调用超时时间 

* 通过设置rpc上下文实现
 RpcContext.getContext().setObjectAttachment("timeout","2000");
 备注：能修改客户端和服务端的超时时间
 
 * 通过修改 ReferenceConfig配置，重新装载服务应该也可以实现 -- 带尝试 
> 参考： 
>com.nanomt88.demo.dubbo.generic.TimeoutTest.timeoutTest();
> com.nanomt88.demo.dubbo.DubboConsumerClientFactory.changeTimeout();

### 泛化调用

> 参考： 
> com.nanomt88.demo.dubbo.DubboConsumerClientFactory.genericInvoke();

### http连接处调用 

>参考：
>com.nanomt88.demo.dubbo.PoolingHttpClientUtils
>com.nanomt88.demo.httpclient.PoolingHttpPoolTest


### 异步转同步 
注意，集群模式下没有消息重试
同步转异步的时候，异步队列里面如何处理超时请求：
* 1.可以使用线程异步扫描
* 2.可以参考dubbo源码，使用future添加的时候，添加一个timer定时检查
>参考 
>com.nanomt88.demo.dubbo.async.AsyncTaskManager

### 动态unregister provider服务
1. 先订阅所有的服务通知，然后获取到url之后就可以反注册
>参考：
>RegistryServerSync.unregisterProvider

## 避坑
### 1. 服务没有提供者 
```
ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
//注意：若provider没有设置版本，则这里也不能设置，设置了就会报no provider的错误。。。。
//reference.setVersion(version);
```

