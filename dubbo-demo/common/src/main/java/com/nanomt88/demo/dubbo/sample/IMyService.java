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


    Order getUser(User name);
}
