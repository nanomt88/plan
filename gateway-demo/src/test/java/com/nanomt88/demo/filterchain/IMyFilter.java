package com.nanomt88.demo.filterchain;

public interface IMyFilter {

    void doFilter(MyRequest request, MyResponse response, MyFilterChain filterChain);
}
