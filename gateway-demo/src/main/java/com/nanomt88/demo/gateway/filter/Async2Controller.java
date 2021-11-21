package com.nanomt88.demo.gateway.filter;

import com.nanomt88.demo.gateway.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author nanomt88@gmail.com
 * @create 2021-08-22 15:36
 **/
@WebServlet(value = "/async2", asyncSupported = true)
@Slf4j
public class Async2Controller extends HttpServlet {

    /**
     * 非异步io + 异步处理样例
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet start....");

        log.info("doGet end....");
    }

    /**
     * 异步io 样例
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost start ....");
        req.setCharacterEncoding("utf-8");
        AsyncContext asyncContext = req.getAsyncContext();
        asyncContext.setTimeout(10 * 1000L);
        ServletInputStream inputStream = req.getInputStream();
        inputStream.setReadListener(new MyReadListener(inputStream, asyncContext));
        log.info("doPost end ....");
    }
}

@Slf4j
class MyReadListener implements ReadListener {

    AsyncContext asyncContext = null;
    ServletInputStream inputStream = null;

    public MyReadListener(ServletInputStream inputStream, AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
        this.inputStream = inputStream;
    }

    /**
     * 数据可用时候触发事件
     *
     * @throws IOException
     */
    @Override
    public void onDataAvailable() throws IOException {
        log.info("数据准备好了可以读取了");
    }

    /**
     * todo 这里有点问题，不知道为啥没有执行？？？
     *
     * @throws IOException
     */
    @Override
    public void onAllDataRead() throws IOException {
        try {
            log.info("数据处理开始了...");
            Thread.sleep(150);
            String request = ServletUtils.readRequestBody(inputStream);
            PrintWriter writer = asyncContext.getResponse().getWriter();
            writer.write(request);
            writer.flush();
            log.info("数据处理完了...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            asyncContext.complete();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("数据读取出错", throwable);
        asyncContext.complete();
    }
}