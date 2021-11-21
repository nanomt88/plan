package com.nanomt88.demo.gateway.filter;

import com.nanomt88.demo.messageconverter.dto.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author nanomt88@gmail.com
 * @create 2021-08-22 15:36
 **/
@RestController
@RequestMapping(value = "/async")
@Slf4j
public class AsyncController{

    @PostMapping(value = "/test")
    @ResponseBody
    public Object asyncDemo(@RequestBody final Person person){
        log.info("person : " + person);
        final DeferredResult<Person> result = new DeferredResult();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("person async body :" + person);
                    Thread.sleep(100);
                    Person person1 = new Person();
                    BeanUtils.copyProperties(person, person1);
                    result.setResult(person1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return result;
    }

}
