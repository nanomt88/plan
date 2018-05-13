#  spring 源码学习
    
    1.spring 源码学习笔记  - 2018.3.13
    
## spring 常见设计模式

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
   
2. 使用场景： 可以在代码执行前面加一些代码，执行完成之后再加一些代码

      spring aop事物原理：
      
        开启事物(open)
        try{
            执行自己的代码
            提交事物(commit)
        }catch(Exception ex){
            回滚事物(rollback)
        }finally{
            关闭事物（close）
        }
    
3. CGlib特点    
    
    //通过设置这个参数可以将cglib运行期间生成的代理类的class文件输出
    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, this.getClass().getClassLoader().getResource("").getPath());

	通过cglib生成的字节码相比jdk实现来说显得更加复杂。
		
	+ cglib自动生成的代理类继承了委托类，且委托类的final方法不能被代理；
	
	+ 代理类为每个委托方法都生成两个方法
	    
			public class UserServiceImpl {  
			    public void add() {  
			        System.out.println("This is add service");  
			    } 
			}
		- 一个是重写的add方法；
		
		 
				public final void add() {
			        MethodInterceptor cglib$CALLBACK_2;
			        MethodInterceptor cglib$CALLBACK_0;
			        if ((cglib$CALLBACK_0 = (cglib$CALLBACK_2 = this.CGLIB$CALLBACK_0)) == null) {
			            CGLIB$BIND_CALLBACKS(this);
			            cglib$CALLBACK_2 = (cglib$CALLBACK_0 = this.CGLIB$CALLBACK_0);
			        }
			        if (cglib$CALLBACK_0 != null) {
			            cglib$CALLBACK_2.intercept((Object)this, UserService$$EnhancerByCGLIB$$394dddeb.CGLIB$add$0$Method, UserService$$EnhancerByCGLIB$$394dddeb.CGLIB$emptyArgs, UserService$$EnhancerByCGLIB$$394dddeb.CGLIB$add$0$Proxy);
			            return;
			        }
			        super.add();
			    }
		- 一个是CGLIB$add$0方法，该方法直接调用委托类的add方方法
		 
				final void CGLIB$add$0() {
			        super.add();
			    }


	+ 当执行代理对象的add方法时，会先判断是否存在实现了MethodInterceptor接口的对象cglib$CALLBACK_0，如果存在，则调用MethodInterceptor对象的intercept方法：

4. cglib原理：
	1. 生成代理类Class的二进制字节码；
	2. 通过Class.forName加载二进制字节码，生成Class对象；
	3. 通过反射机制获取实例构造，并初始化代理类对象。
	
5. jdk和cglib动态代理实现的区别
	1. jdk动态代理生成的代理类和委托类实现了相同的接口；
	2. cglib动态代理中生成的字节码更加复杂，生成的代理类是委托类的子类，且不能处理被final关键字修饰的方法；CGlib是采用继承的方式实现动态代理的，因此final类不能代理，非final类中的final方法也不能被代理
	3. jdk采用反射机制调用委托类的方法，cglib采用类似索引的方式直接调用委托类方法；

6. FastClass实现机制

	FastClass其实就是对Class对象进行特殊处理，提出下标概念index，通过索引保存方法的引用信息，将原先的反射调用，转化为方法的直接调用，从而体现所谓的fast，下面通过一个例子了解一下FastClass的实现机制。
	1. 定义原类

		class Test {
		    public void f(){
		        System.out.println("f method");
		    }
		    
		    public void g(){
		        System.out.println("g method");
		    }
		}
	2. 定义Fast类

		class FastTest {
		    public int getIndex(String signature){
		        switch(signature.hashCode()){
		        case 3078479:
		            return 1;
		        case 3108270:
		            return 2;
		        }
		        return -1;
		    }
		
		    public Object invoke(int index, Object o, Object[] ol){
		        Test t = (Test) o;
		        switch(index){
		        case 1:
		            t.f();
		            return null;
		        case 2:
		            t.g();
		            return null;
		        }
		        return null;
		    }
		}
	
	在FastTest中有两个方法，getIndex中对Test类的每个方法根据hash建立索引，invoke根据指定的索引，直接调用目标方法，避免了反射调用。所以当调用methodProxy.invokeSuper方法时，实际上是调用代理类的CGLIB$add$0方法，CGLIB$add$0直接调用了委托类的add方法。


## spring IOC源码

## spring MVC源码

## spring 事物
    
## Spring 面试题
    