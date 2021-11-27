package com.siyue.solar.solarsystem;

import java.io.*;
import java.util.Arrays;

/**
 * @author nanomt88@gmail.com
 * @create 2020-09-26 21:11
 **/
public class FileTest {

    public static void main(String[] args) {
        File rootPath = new  File("E:\\学习\\JAVA视频\\Java工程师面试突击：第1季");

        File[] files = rootPath.listFiles();

        Arrays.asList(files).forEach((parent) -> {

            if(parent.isFile()){
                System.out.println("跳过：" + parent.getAbsolutePath());
                return;
            }
            File[] childs = parent.listFiles();

            if(childs == null || childs.length < 1){
                parent.delete();
            }

            if(childs != null && childs.length >= 1){

                Arrays.asList(childs).forEach((cc) ->{


                    String path = cc.getName();

                    if(cc.isDirectory() || !path.endsWith(".avi")){
                        System.out.println("跳过：" + cc.getAbsolutePath());
                        return;
                    }
                    String fileName = parent.getAbsolutePath() + "_" +cc.getName() ;

                    System.out.print("move 原：" + cc.getAbsolutePath());
                    System.out.println(" to 新：" + fileName);
                    moveFile(cc.getAbsolutePath(), fileName);
                });

            }

//            throw new RuntimeException();
        });

    }


    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);

    }


    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错 ");
            e.printStackTrace();

        }

    }


    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            java.io.File myDelFile = new java.io.File(filePath);
            myDelFile.delete();

        } catch (Exception e) {
            System.out.println("删除文件操作出错 ");
            e.printStackTrace();

        }
    }

}
