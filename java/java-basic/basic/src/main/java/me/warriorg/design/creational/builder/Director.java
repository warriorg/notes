package me.warriorg.design.creational.builder;

public class Director {
    private Builder builder = new ProductBuilder();
    public Product getAProduct(){
        builder.setName("宝马汽车");
        builder.setType("X7");
        return builder.getProduct();
    }
    public Product getBProduct(){
        builder.setName("奥迪汽车");
        builder.setType("Q5");
        return builder.getProduct();
    }
}
