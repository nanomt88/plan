package com.nanomt88.demo.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nanomt88@gmail.com
 * @create 2021-08-28 20:39
 **/
@WebListener
public class AppContextListener implements ServletContextListener {

    ThreadPoolExecutor threadPoolExecutor = null;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        threadPoolExecutor = new ThreadPoolExecutor(10, 20,
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(5000));
        sce.getServletContext().setAttribute("workThreadPool", threadPoolExecutor);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(threadPoolExecutor != null){
            threadPoolExecutor.shutdown();
        }
    }
}
