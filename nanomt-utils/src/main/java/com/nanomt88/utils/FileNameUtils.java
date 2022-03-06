package com.nanomt88.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 文件名处理工具类
 *
 * @author nanomt88@gmail.com
 * @create 2022/3/5 11:17
 **/
public class FileNameUtils {

    /**
     * 批量修改删除文件名部分内容
     * @param fileDirPath
     * @param deleteString
     */
    public static void batchRemoveFileName(String fileDirPath, String deleteString){
        File[] files = readDirectoryFiles(fileDirPath);
        if(files == null || files.length < 1){
            return;
        }
        Arrays.asList(files).forEach( file -> {
            if(file.isDirectory()){
                return;
            }
            String fileName = file.getName();
            if(!fileName.contains(deleteString)){
                return;
            }
            fileName = fileName.replace(deleteString, "").trim();
            String dir = file.getParent();
            String newName = dir + File.separator + fileName;
            System.out.println("rename file " + file.getName() + "to " + fileName);
            file.renameTo(new File(newName));
        });
    }

    private static File[] readDirectoryFiles(String fileDirPath){
        if(StringUtils.isBlank(fileDirPath)){
            return null;
        }
        File fileDir = new File(fileDirPath);
        if(fileDir == null || !fileDir.isDirectory()){
            return null;
        }
        File[] files = fileDir.listFiles();
        return files;
    }


    public static void main(String[] args) {
        batchRemoveFileName("D:\\学习\\Java\\奈学\\P7\\document\\课件", "《P7架构师11期》");
        batchRemoveFileName("D:\\学习\\Java\\奈学\\P7\\document\\课件", "《P7架构师10期》");
    }
}
