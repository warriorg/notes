package me.warriorg.design.creational.builder;

public class Client {

    public static void main(final String[] args) {
        Director director = new Director();
        Product aProduct = director.getAProduct();
        aProduct.showProduct();

        Product bProduct = director.getBProduct();
        bProduct.showProduct();
    }
}
