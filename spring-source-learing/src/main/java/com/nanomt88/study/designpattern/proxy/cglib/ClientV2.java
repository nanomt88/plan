package com.nanomt88.study.designpattern.proxy.cglib;

import com.nanomt88.study.designpattern.proxy.BeijingLoser;
import com.nanomt88.study.designpattern.proxy.House;
import com.nanomt88.study.designpattern.proxy.Renter;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Test;

public class ClientV2 {

    @Test
    public void test(){
        //设置此参数可以显示运行期间 cglib生成的代理类class文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, this.getClass().getClassLoader().getResource("").getPath());
        BadAgentV2 proxy = new BadAgentV2();
        BeijingLoser instance = (BeijingLoser) proxy.getInstance(BeijingLoser.class, "北漂小屌丝",
                new House(false,false,1000,5));
        instance.findHouse(new House(true,true,1000, 10));
    }
}
