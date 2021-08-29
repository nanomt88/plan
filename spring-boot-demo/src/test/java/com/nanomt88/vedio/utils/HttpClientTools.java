package com.nanomt88.vedio.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author nanomt88@gmail.com
 * @create 2019-09-12 23:16
 **/
public class HttpClientTools {


    public static void main(String[] args) throws UnknownHostException {

        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ip);
    }
}
