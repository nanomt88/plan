package com.nanomt88.demo.messageconverter.controller;

import com.nanomt88.demo.messageconverter.dto.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nanomt88@gmail.com
 * @create 2019-07-27 9:27
 **/
@RestController
public class HelloController {


    @PostMapping("/")
    public Object hello(@RequestBody Person person){
        System.out.println("person : " + person);

        Map<String,String> map = new HashMap<>();
        map.put("response", "000000");
        map.put("message", person.getName());
        map.put("name", person.getName());
        map.put("id", person.getId()+"");
        return map;
    }

    @PostMapping(value = "/jsonToProperties", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = "application/properties+person")
    public Object jsonToProperties(@RequestBody Person person){
        System.out.println("person : " + person);
        return person;
    }

    @PostMapping(value = "/propertiesToJson", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = "application/properties+person")
    public Object propertiesToJson(@RequestBody Person person){
        System.out.println("person : " + person);
        return person;
    }
}
