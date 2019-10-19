package com.nanomt88.study;

import org.junit.Test;

/**
 * @author nanomt88@gmail.com
 * @create 2019-06-09 19:51
 **/
public class LogTest {

    @Test
    public void test(){
        long time = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("----------- logStackTrace start ----------- ts=" ).
                append(time).append(System.getProperty("line.separator"));
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i = 0 ;
        for(StackTraceElement stack : stackTrace){
            sb.append("|");
            for(int j=0; j<i; j++){
                sb.append("   ");
            }
            sb.append(".").append(stack.getMethodName()).append("       (ts=").
                    append(time).append(")").append(System.getProperty("line.separator"));
        }
        sb.append("----------- logStackTrace end ----------- ts=" ).append(time).
                append(System.getProperty("line.separator"));
        System.out.println(sb.toString());

        try {

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test11(){
        System.out.println("-----------");
        printTrack();
    }

    public  void printTrack(){
        String time = System.currentTimeMillis()+"";
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        if(st==null){
            System.out.println("无堆栈...");
            return;
        }
        StringBuilder sbf =new StringBuilder();
        sbf.append("----------- logStackTrace start ----------- (ts=" ).append(time).append(")").
                append(System.getProperty("line.separator"));
        int i = 0;
        for(StackTraceElement e:st){
            sbf.append("|");
            for(int j=0; j<i; j++){
                sbf.append("  ");
            }
            i++;
            sbf.append(java.text.MessageFormat.format("{0}.{1} ({2}:{3})\t(ts={4})"
                    ,e.getClassName()
                    ,e.getMethodName(),e.getFileName()
                    ,e.getLineNumber(), time));
            sbf.append(System.getProperty("line.separator"));
        }
        sbf.append("----------- logStackTrace end -----------       (ts=" ).append(time).append(")").
                append(System.getProperty("line.separator"));
        System.out.println(sbf.toString());
    }
}
