package com.nanomt88.demo.filterchain;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThirdFilter implements IMyFilter{
    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain filterChain) {
        log.info("do third in, request : [{}], response:[{}]]" ,request, response);
        request.setContent(request.getContent() + "+3");

        filterChain.doFilter(request,response);
        response.setContent(response.getContent()+"+3");

        log.info("do third out, request : [{}], response:[{}]" ,request, response);
    }
}
