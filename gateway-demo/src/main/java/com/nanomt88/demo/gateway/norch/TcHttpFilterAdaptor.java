package com.nanomt88.demo.gateway.norch;


import com.nanomt88.demo.gateway.channel.ITcChannel;
import com.nanomt88.demo.gateway.channel.router.ITcRouter;
import com.nanomt88.demo.gateway.common.TcHttpRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import com.nanomt88.demo.gateway.utils.ServletUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * http请求的入口适配类 ，每一个请求协议的类型会有一个这样的适配器的类
 * 当http请求过来之后，会对报文进行解析，解析后交由后面的 TcChannel（通道）执行
 */
@WebFilter(filterName = "myFilter" , urlPatterns = "/*")
@Slf4j
@Data
public class TcHttpFilterAdaptor implements Filter {

    @Autowired
    private ITcRouter router ;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletInputStream inputStream = servletRequest.getInputStream();

        String messageBody = ServletUtils.readRequestBody(inputStream);

        log.info("请求开始，长度：[{}]" , messageBody == null ? 0 : messageBody.length());

        TcHttpRequest request = new TcHttpRequest();
        request.setOriginRequestBody(messageBody.getBytes(StandardCharsets.UTF_8));
        request.setOriginRequestString(messageBody);
        request.setUrl(httpServletRequest.getRequestURI());
        request.setHttpServletRequest(httpServletRequest);

        TcResponse tcResponse = new TcResponse();
        if(router != null){
            ITcChannel channel = router.selectChannel(request);
            channel.doInnerProcess(request,  tcResponse);
        }
        servletResponse.setCharacterEncoding(tcResponse.getCharset().toString());
        servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        servletResponse.getWriter().write(tcResponse.getOriginResponseString());
    }



}
