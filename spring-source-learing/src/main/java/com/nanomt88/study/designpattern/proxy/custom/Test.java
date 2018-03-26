package com.nanomt88.study.designpattern.proxy.custom;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

import com.nanomt88.study.designpattern.proxy.custom.MyInvocationHandler;

public final class Test implements com.nanomt88.study.designpattern.proxy.Renter {
    private MyInvocationHandler h;
    private static Method m0;
    private static Method m1;
    private static Method m2;

    static {
        try {
            m0 = Class.forName("com.nanomt88.study.designpattern.proxy.custom.$Proxy0").getMethod("getName", new Class[0]);
            m1 = Class.forName("com.nanomt88.study.designpattern.proxy.custom.$Proxy0").getMethod("findHouse", new Class[]{Class.forName("com.nanomt88.study.designpattern.proxy.House")});
            m2 = Class.forName("com.nanomt88.study.designpattern.proxy.custom.$Proxy0").getMethod("getExpect", new Class[0]);
        } catch (NoSuchMethodException localNoSuchMethodException) {
            throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
        } catch (ClassNotFoundException localClassNotFoundException) {
            throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
        }
    }

    private Test(MyInvocationHandler h) {
        this.h = h;
    }

    public final java.lang.String getName() {
        try {
            return (java.lang.String) this.h.invoke(this, m0, null);
        } catch (Error | RuntimeException localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final boolean findHouse(com.nanomt88.study.designpattern.proxy.House var0) {
        try {
            return (boolean) this.h.invoke(this, m1, new Object[]{var0});
        } catch (Error | RuntimeException localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final com.nanomt88.study.designpattern.proxy.House getExpect() {
        try {
            return (com.nanomt88.study.designpattern.proxy.House) this.h.invoke(this, m2, null);
        } catch (Error | RuntimeException localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }
}