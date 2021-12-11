package com.nanomt88.demo.dubbo.sample;

import org.springframework.stereotype.Component;


//@Component
//@Service
//@Path("user")
public interface IMyService {

//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_JSON})
    String sayHello(String name);

    /**
     * 异步转同步示例
     * @param name
     * @return
     */
    Order asyncToSync(User name);


    Order getUser(User name);
}
