package me.warriorg.design.creational.singleton;

/**
 * 单例模式——静态内部类方式
 *
 * @author warrior
 */
public class StaticSingleton {

    private StaticSingleton() {
    }


    public static StaticSingleton getInstance() {
        return StaticSingletonHolder.instance;
    }

    /**
     * 一个私有的静态内部类，用于初始化一个静态final实例
     */
    private static class StaticSingletonHolder {
        private static final StaticSingleton instance = new StaticSingleton();
    }

}
