package me.warriorg.design.creational.builder;

public interface Builder {

    Product getProduct();

    void setName(String name);

    void setType(String type);
}
