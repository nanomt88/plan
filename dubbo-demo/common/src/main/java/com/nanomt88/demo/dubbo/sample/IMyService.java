package com.nanomt88.demo.dubbo.sample;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Service
@Path("user")
public interface IMyService {

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    String sayHello(String name);


    Order sayHello(User name);
}
