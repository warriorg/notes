package me.warriorg.design.structural.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CGLibProxyApp {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Subject.class);
        enhancer.setCallback(new MyInvocationHandler());
        Subject subject = (Subject) enhancer.create();
        subject.sellBooks();
        subject.speak();
    }
}
