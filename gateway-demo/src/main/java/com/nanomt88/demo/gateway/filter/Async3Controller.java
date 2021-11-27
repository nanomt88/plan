package com.nanomt88.demo.gateway.filter;

import com.nanomt88.demo.gateway.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 例子是异步controller的处理方式
 * @author nanomt88@gmail.com
 * @create 2021-08-22 15:36
 **/
@WebServlet(value = "/async3", asyncSupported = true)
@Slf4j
public class Async3Controller extends HttpServlet {

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
        AsyncContext asyncContext = req.getAsyncContext();

        ThreadPoolExecutor workThreadPool = (ThreadPoolExecutor) req.getServletContext().getAttribute("workThreadPool");
        workThreadPool.submit(() -> {
            PrintWriter writer = null;
            try {
                String request = ServletUtils.readRequestBody(asyncContext.getRequest().getInputStream());
                log.info("读取到数据：{}", request);
                writer = asyncContext.getResponse().getWriter();

                asyncContext.getResponse().setCharacterEncoding("utf-8");
                asyncContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                writer.write(request);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncContext.complete();
            log.info("数据处理完了...");
        });

        log.info("doPost end ....");
    }
}
