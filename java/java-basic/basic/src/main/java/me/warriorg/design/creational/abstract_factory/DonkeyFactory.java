package me.warriorg.design.creational.abstract_factory;

public class DonkeyFactory extends AnimalFactory {

    @Override
    public Animal createAnimal() {
        return new Donkey();
    }
}
