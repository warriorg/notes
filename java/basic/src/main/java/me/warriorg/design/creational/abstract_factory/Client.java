package me.warriorg.design.creational.abstract_factory;

public class Client {
    public static void main(final String[] args) {
        AnimalFactory factory = null;
        final String name = randomAnimal();
        if ("dog".equals(name)) {
            factory = new DogFactory();
        } else {
            factory = new DonkeyFactory();
        }
        final Animal animal = factory.createAnimal();
        animal.call();
    }

    public static String randomAnimal() {
        final String[] appearanceArray = new String[3];

        appearanceArray[0] = "dog";
        appearanceArray[1] = "donkey";
        final java.util.Random random = new java.util.Random();

        final int randomNumber = random.nextInt(2);

        return appearanceArray[randomNumber];
    }
}
