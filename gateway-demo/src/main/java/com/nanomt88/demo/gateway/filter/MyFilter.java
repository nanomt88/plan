package com.nanomt88.demo.gateway.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * filter 示范类
 *
 * @author nanomt88@gmail.com
 * @create 2021-08-22 15:38
 **/

//@WebFilter(filterName = "myFilter" , urlPatterns = "/*", asyncSupported = true)
@Slf4j
public class MyFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("MyFilter invoker start ...");
        //获取异步上下文对象
        final AsyncContext asyncContext = request.startAsync();
        //添加异步监听类，作用是监听 filter异步执行的事件
        asyncContext.addListener( new AppAsyncListener());
        //异步超时时间，等待超时之后就返回
        asyncContext.setTimeout(10*1000L);
        //获取工作线程，讲任务异步添加到工作线程池中
//        ThreadPoolExecutor workThreadPool = (ThreadPoolExecutor) request.getServletContext().getAttribute("workThreadPool");
        //提交异步任务
//        workThreadPool.submit(new AsyncRequestProcessor(asyncContext));
        log.info("MyFilter submit task ...");
//        HttpServletRequest req = (HttpServletRequest) request;
//        Principal userPrincipal = req.getUserPrincipal();
        chain.doFilter(request, response);
        log.info("MyFilter invoker end ...");
    }
}
