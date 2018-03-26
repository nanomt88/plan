package com.nanomt88.study.designpattern.proxy.custom;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

public class MyClassLoader extends ClassLoader{

    private String basePath ;

    public MyClassLoader(String path) {
        this.basePath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(basePath, name);
        if(file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len ;
                while ( (len = in.read(buffer)) != -1){
                    out.write(buffer,0, len);
                }
                return defineClass(name, out.toByteArray(), 0, out.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return super.findClass(name);
    }
}
