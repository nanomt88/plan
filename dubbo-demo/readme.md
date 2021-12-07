
## dubbo学习 
### 动态设置duubo调用超时时间 

* 通过设置rpc上下文实现
 RpcContext.getContext().setObjectAttachment("timeout","2000");
 * 通过修改 ReferenceConfig配置，重新装载服务应该也可以实现 -- 带尝试 
> 参考： 
>com.nanomt88.demo.dubbo.generic.TimeoutTest.timeoutTest();
> com.nanomt88.demo.dubbo.DubboConsumerClientFactory.changeTimeout();

### 泛化调用

> 参考： 
> com.nanomt88.demo.dubbo.DubboConsumerClientFactory.genericInvoke();



## 避坑
### 1. 服务没有提供者 
```
ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
//注意：若provider没有设置版本，则这里也不能设置，设置了就会报no provider的错误。。。。
//reference.setVersion(version);
```
