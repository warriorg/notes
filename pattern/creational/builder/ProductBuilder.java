package me.warriorg.design.creational.builder;

public class ProductBuilder implements Builder {
    private Product product = new Product();

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setName(String name) {
        product.setName(name);
    }

    @Override
    public void setType(String type) {
        product.setType(type);
    }
}
