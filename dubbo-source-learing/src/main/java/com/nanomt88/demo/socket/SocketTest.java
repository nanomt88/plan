package com.nanomt88.demo.socket;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author nanomt88@gmail.com
 * @create 2021-03-17 13:25
 **/
public class SocketTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        SocketAddress address = new InetSocketAddress(8080);
        serverSocket.bind(address);
        System.out.println("服务端启动了。。。");
        while (true){
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;
            while ( (s = reader.readLine()) == null){
                System.out.println(s);
            }
            System.out.println("sleep 1000ms");
            Thread.sleep(1000);
        }
    }

    public void cache(){
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        ReferenceConfig<CacheService> service = new ReferenceConfig<CacheService>();
        CacheService cacheService = cache.get(service);

        LinkedBlockingQueue queue = new LinkedBlockingQueue(100);

    }
}

interface CacheService{

}