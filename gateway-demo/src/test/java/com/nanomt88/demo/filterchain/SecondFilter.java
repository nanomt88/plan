package com.nanomt88.demo.filterchain;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecondFilter implements IMyFilter{
    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain filterChain) {
        log.info("do second in, request : [{}], response:[{}]]" ,request, response);
        request.setContent(request.getContent() + "+2");
        filterChain.doFilter(request,response);
        response.setContent(response.getContent()+"+2");
        log.info("do second out, request : [{}], response:[{}]" ,request, response);

    }
}
