package com.nanomt88.study.designpattern.proxy.custom;

import com.nanomt88.study.designpattern.proxy.BeijingLoser;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MyProxy {

    private static final String ln = "\r\n";
//    private static MyInvocationHandler handler;

    public static final Object newProxyInstance(MyClassLoader classLoader, Class<?>[] clazz, MyInvocationHandler obj) {
//        handler = obj;
        //1. 生成Java源代码
        String source = generateSource(clazz);
        System.out.println("============================");
        //2. 写到本地磁盘
        File file = writeSource(source);
        System.out.println("Java file path:"+file);
        //3. 编译
        boolean result = compilerSource(file);
        System.out.println("生成Class：" + result);
        //4. ClassLoader 加载类
        try {
            String fileName = file.getName();
            fileName = fileName.replace(".java","");
            Class<?> aClass = classLoader.loadClass(MyProxy.class.getPackage().getName() +"."+ fileName);
            Constructor<?> constructor = aClass.getConstructor(MyInvocationHandler.class);
            return constructor.newInstance(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean compilerSource(File file) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, Charset.defaultCharset());
        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        return task.call();
    }

    private static File writeSource(String source) {
        String path = MyProxy.class.getResource("").getPath()
                + "/$Proxy0.java";
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(source);
            fileWriter.flush();
            fileWriter.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSource(Class<?>[] clazz) {

        List<Method> list = new ArrayList<Method>();
        for (Class<?> c : clazz) {
            Method[] methods = c.getMethods();
            for (Method m : methods) {
                list.add(m);
            }
        }

        StringBuffer str = new StringBuffer();
        str.append("package ").append(MyProxy.class.getPackage().getName()).append(";").append(ln);
        str.append("import java.lang.reflect.Method;").append(ln);
        str.append("import java.lang.reflect.UndeclaredThrowableException;").append(ln);
        str.append("import ").append(MyInvocationHandler.class.getName()).append(";").append(ln);
        Class<?>[] interfaces = clazz;
        str.append("public final class $Proxy0 implements ");
        for (Class<?> intf : interfaces) {
            str.append(intf.getName()).append(",");
        }
        str.delete(str.length() - 1, str.length());
        str.append(" { ").append(ln);
        str.append("    private MyInvocationHandler h;").append(ln);
        //待反射生成的类
        Method[] methods = list.toArray(new Method[]{});
        for (int i = 0; i < methods.length; i++) {
            str.append("private static Method m").append(i).append(";").append(ln);
        }
        //内容
        str.append("static  {").append(ln);
        str.append("   try{").append(ln);

        for (int i = 0; i < methods.length; i++) {
            str.append("        m").append(i).append(" = Class.forName(\"").append(MyProxy.class.getPackage().getName())
                    .append(".$Proxy0\").getMethod(\"").append(methods[i].getName()).append("\"");
            Class<?>[] parameterTypes = methods[i].getParameterTypes();
            str.append(",new Class[").append(methods[i].getParameterCount() == 0 ? 0 : "").append("]");
            if (methods[i].getParameterCount() > 0) {
                str.append("{");
                for (Class<?> params : parameterTypes) {
                    str.append("Class.forName(\"" + params.getName() + "\"),");
                }
                str.delete(str.length() - 1, str.length());
                str.append("}");
            }
            str.append(");").append(ln);
        }
        str.append("    }catch (NoSuchMethodException localNoSuchMethodException) {").append(ln);
        str.append("      throw new NoSuchMethodError(localNoSuchMethodException.getMessage());").append(ln);
        str.append("    }catch (ClassNotFoundException localClassNotFoundException){").append(ln);
        str.append("      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());").append(ln);
        str.append("    }").append(ln);
        str.append("}").append(ln);
        //构造方法
        str.append("    public $Proxy0(MyInvocationHandler h){").append(ln);
        str.append("        this.h = h;").append(ln);
        str.append("    }").append(ln);
        //方法体
        for (int i = 0; i < methods.length; i++) {
            str.append("    public final ").append(methods[i].getReturnType().getName()).append(" ").append(methods[i].getName())
                    .append("(");
            if (methods[i].getParameterCount() > 0) {
                Class<?>[] parameterTypes = methods[i].getParameterTypes();
                for (int j = 0; j < parameterTypes.length; j++) {
                    str.append(parameterTypes[j].getName()).append(" var").append(j).append(" ,");
                }
                str.delete(str.length() - 1, str.length());
            }
            str.append(")");

            Class<?>[] exceptions = methods[i].getExceptionTypes();
            if (exceptions != null && exceptions.length > 0) {
                str.append("throws ");
                for (Class<?> ex : exceptions) {
                    str.append(ex.getName()).append(",");
                }
                str.delete(str.length() - 1, str.length());
            }
            str.append("{").append(ln);
            str.append("        try{").append(ln);
            if ("void".equals(methods[i].getReturnType().getName())) {
                str.append("          this.h.invoke(this, m" + i + ", ");
            } else {
                str.append("          return ").append("(" + methods[i].getReturnType().getName() + ")")
                        .append("this.h.invoke(this, m" + i + ", ");
            }
            if (methods[i].getParameterCount() > 0) {
                str.append("new Object[] {");
                Class<?>[] parameterTypes = methods[i].getParameterTypes();
                for (int j = 0; j < parameterTypes.length; j++) {
                    str.append(" var").append(j).append(" ,");
                }
                str.delete(str.length() - 1, str.length());
                str.append("}");
            } else {
                str.append("null");
            }
            str.append(");").append(ln);

            str.append("        }catch (Error|RuntimeException localError){").append(ln);
            str.append("          throw localError;").append(ln);
            str.append("        }catch (Throwable localThrowable){").append(ln);
            str.append("          throw new UndeclaredThrowableException(localThrowable);").append(ln);
            str.append("        }").append(ln);
            str.append("    }").append(ln);
        }
        str.append("}");
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.printf(MyInvocationHandler.class.getPackage().getName());
        MyProxy.newProxyInstance(new MyClassLoader(MyBadAgent.class.getResource("/").getPath()), BeijingLoser.class.getInterfaces(), new MyBadAgent());
    }
}
