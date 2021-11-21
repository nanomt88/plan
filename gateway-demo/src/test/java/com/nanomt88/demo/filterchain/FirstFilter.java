package com.nanomt88.demo.filterchain;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstFilter implements IMyFilter{
    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain filterChain) {
        log.info("do first in, request : [{}], response:[{}]]" ,request, response);
        request.setContent(request.getContent() + "+1");
        filterChain.doFilter(request,response);
        response.setContent(response.getContent()+"+1");
        log.info("do first out, request : [{}], response:[{}]" ,request, response);
    }
}
