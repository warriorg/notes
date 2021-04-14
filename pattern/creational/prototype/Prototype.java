package me.warriorg.design.creational.prototype;

public class Prototype implements Cloneable {

    public Prototype clone() throws CloneNotSupportedException{
        return (Prototype) super.clone();
    }
}
