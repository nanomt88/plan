package com.nanomt88.study.designpattern.proxy.cglib;

import com.nanomt88.study.designpattern.proxy.BeijingLoser;
import com.nanomt88.study.designpattern.proxy.House;
import com.nanomt88.study.designpattern.proxy.Renter;
import org.junit.Test;

public class ClientV2 {

    @Test
    public void test(){
        BadAgentV2 proxy = new BadAgentV2();
        BeijingLoser instance = (BeijingLoser) proxy.getInstance(BeijingLoser.class, "北漂小屌丝",
                new House(false,false,1000,5));
        instance.findHouse(new House(true,true,1000, 10));
    }
}
