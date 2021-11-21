package com.nanomt88.demo.filterchain;

import java.util.ArrayList;
import java.util.List;

public class MyFilterChain{

    List<IMyFilter> filterList;
    int index = 0 ;

    public void addFilter(IMyFilter filter){
        if(filterList == null){
            filterList = new ArrayList<>();
        }
        filterList.add(filter);
    }

    public void addFilters(List<IMyFilter> filters){
        if(filterList == null){
            this.filterList = new ArrayList<>();
        }
        filterList.addAll(filters);
    }

    public void doFilter(MyRequest request, MyResponse response) {
        //这里的实现方式是Tomcat，Spring,中的实现方式，当然更见单的实现方式是每个filter持有下一个filter的引用，
        // 处理完成之后，直接调用nextFilter的方法。直到执行完毕。显然第二种在编码上更简单也更容易，
        // 那么为什么许多java框架都使用第一种框架而不是使用第二种框架？个人认为i显然在可扩展上要比低一种方式根号。
        // FilterChain 持有Filter列表的管理和控制，这样就filter-chain链的管理集中的地方，实现了维护和扩展方便。
        //
        //Tomcat仅仅是在反射的基础之上加上了Filter的使用。
        if(index < filterList.size()){
            IMyFilter myFilter = filterList.get(index++);
            myFilter.doFilter(request,response, this);
        }
    }
}
