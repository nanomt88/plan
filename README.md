# 学习计划
    
    1.spring 源码学习笔记  - 2018.3.13
    
## spring 源码学习

### spring 常见设计模式 - JDK动态代理
1. 类图
    
![类图](https://raw.githubusercontent.com/nanomt88/plan/master/spring-source-learing/src/main/java/com/nanomt88/study/designpattern/proxy/proxy.png)
    
2. 步骤
	
	1. 自定义ProxyAgent类 实现InvocationHandle接口，实现invoke方法
	2. ProxyAgent类中提供一个 getInstance类似的方法，方法中返回代理对象
		 Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this)
	3. Client中调用是，通过ProxyAgent.getInstance 获取被代理对象，调用被代理方法即可执行。


3. 自己实现Java 动态代理全过程

    1. 生成源代码，自己将生成的$Proxy0的java源代码
    2. 将生成的源代码输出到磁盘
    3. 编译源代码，并且生成 .class文件（通过JavaCompiler编译）
    4. 将class文件中的内容，动态的加载到JVM中（自定义ClassLoader，加载$Proxy0类）
    5. 返回被代理后的代理对象，即可完成调用

	**备注**：JDK中，生产的动态代理对象 $Proxy.class类是通过 直接生产class字节码的方式，此处使用源代码编译的方式

	代码参考 [自己实现Java动态代理源代码](https://github.com/nanomt88/plan/tree/master/spring-source-learing/src/main/java/com/nanomt88/study/designpattern/proxy/custom "自己实现Java动态代理源代码")


### spring 常见设计模式 - CGlib 动态代理
1. 满足代理模式的三个必要条件
    
    1. 需要两个角色：执行者和被代理对象
    2. 注重过程，必须要做，被代理对象没时间做或者不想做
    3. 执行者必须拿到被代理对象的个人资料（执行者持有被代理对象的引用）
    