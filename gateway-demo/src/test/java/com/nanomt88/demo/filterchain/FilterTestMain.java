package com.nanomt88.demo.filterchain;

import org.junit.jupiter.api.Test;

public class FilterTestMain {

    @Test
    public void testFilters(){
        FirstFilter first = new FirstFilter();
        SecondFilter second = new SecondFilter();
        ThirdFilter third = new ThirdFilter();

        MyFilterChain chain = new MyFilterChain();
        chain.addFilter(first);
        chain.addFilter(second);
        chain.addFilter(third);

        chain.doFilter(new MyRequest("mmmyRequest"), new MyResponse("mmmyResponse"));


    }
}
