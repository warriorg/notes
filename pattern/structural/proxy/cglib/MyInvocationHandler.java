package me.warriorg.design.structural.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyInvocationHandler implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("调用代理类");
        if (method.getName().equals("sellBooks")) {
            System.out.println("调用的是卖书的方法");
            return 1;
        } else {
            System.out.println("调用的是说话的方法");
            return "hhh";
        }
    }
}
