package com.nanomt88.demo.dubbo.virtual;

import java.lang.reflect.Method;
import java.util.Arrays;

public class VirtualDubboApiInfo<T> {

    private String apiCode;

    /**
     * spring bean对象
     */
    private T bean;

    private Class<T> targetClass;

    private Class<T> targetInterface;

    private Method targetMethod;

    Class<?>[] parameterTypes;

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class<T> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    @Override
    public String toString() {
        return "VirtualDubboApiInfo{" +
                "apiCode='" + apiCode + '\'' +
                ", bean=" + bean +
                ", targetClass=" + targetClass +
                ", targetInterface=" + targetInterface +
                ", targetMethod=" + targetMethod +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                '}';
    }
}
