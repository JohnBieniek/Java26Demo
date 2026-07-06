package com.JohnBieniek.Java26Demo.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.JohnBieniek.Java26Demo.model.animal.AnimalType;
import com.JohnBieniek.Java26Demo.model.animal.Beetle;
import com.JohnBieniek.Java26Demo.model.animal.Bird;
import com.JohnBieniek.Java26Demo.model.animal.Dog;
import com.JohnBieniek.Java26Demo.model.animal.abstracts.Animal;

@Service
public class AnimalManager {
    private static final Logger logger = LoggerFactory.getLogger(AnimalManager.class);

    /**
     * Demonstrates invoking a method declared by the sealed Flys interface.
     */
    public String demoInterface() {
        Beetle beetle = new Beetle();
        String flight = beetle.fly();
        logger.info("The beetle takes flight: {}", flight);
        return "The beetle takes flight: " + flight;
    }

    /**
     * Demonstrates a Java 15 text block for readable multiline text.
     */
    public String animalGuide() {
        return """
                Animal Guide
                ------------
                Beetle: An animal that implements the sealed Flys interface.
                Bird: An animal that implements the sealed Flys interface.
                Dog: An animal that does not fly.
                """;
    }

    /**
     * Demonstrates a Java 14 switch expression that returns a value without fall-through.
     */
    public String describeAnimal(AnimalType animalType) {
        return switch (animalType) {
            case BEETLE -> "A beetle is an insect that can open its wings and fly.";
            case BIRD -> "A bird has feathers and can fly.";
            case DOG -> "A dog is a land animal that cannot fly.";
        };
    }

    /**
     * Demonstrates Java 16 pattern matching for instanceof, which tests a subtype
     * and binds a typed variable without a separate cast.
     */
    public String identifyAnimal(AnimalType animalType) {
        Animal animal = switch (animalType) {
            case BEETLE -> new Beetle();
            case BIRD -> new Bird();
            case DOG -> new Dog();
        };

        if (animal instanceof Beetle beetle) {
            return "Identified a Beetle. It says " + beetle.speak() + " and can fly: " + beetle.fly();
        }
        if (animal instanceof Bird bird) {
            return "Identified a Bird. It says " + bird.speak() + " and can fly: " + bird.fly();
        }
        if (animal instanceof Dog dog) {
            return "Identified a Dog. It says " + dog.speak();
        }

        return "Unknown animal.";
    }

    /**
     * Demonstrates polymorphism by referencing a Beetle through the Animal base type.
     */
    public String feedBeetle() {
        Animal beetle = new Beetle();
        String result = "You feed the beetle." + beetle.eat();
        logger.info("You feed the beetle: {}", beetle.eat());
        return result;
    }

    /**
     * Demonstrates polymorphism by referencing a Bird through the Animal base type.
     */
    public String feedBird() {
        Animal bird = new Bird();
        String result = "You feed the bird." + bird.eat();
        logger.info("You feed the bird: {}", bird.eat());
        return result;
    }

    /**
     * Demonstrates polymorphism by referencing a Dog through the Animal base type.
     */
    public String feedDog() {
        Animal dog = new Dog();
        String result = "You feed the dog." + dog.eat();
        logger.info("You feed the dog: {}", dog.eat());
        return result;
    }

    /**
     * Demonstrates an overridden method by invoking Dog.speak().
     */
    public String petDog() {
        Dog dog = new Dog();
        String result = "You pet the dog. It says: " + dog.speak();
        logger.info("Petting the dog, it says: {}", dog.speak());
        return result;
    }

    /**
     * Demonstrates the inherited static sleep behavior for Beetle.
     */
    public String sedateBeetle() {
        String result = "You sedate a beetle." + Beetle.sleep();
        logger.info("You sedate a beetle: {}", Beetle.sleep());
        return result;
    }

    /**
     * Demonstrates the inherited static sleep behavior for Bird.
     */
    public String sedateBird() {
        String result = "You sedate a bird." + Bird.sleep();
        logger.info("You sedate a bird: {}", Bird.sleep());
        return result;
    }

    /**
     * Demonstrates Dog's static method hiding of Animal.sleep().
     */
    public String sedateDog() {
        String result = "You sedate a dog." + Dog.sleep();
        logger.info("You sedate a dog: {}", Dog.sleep());
        return result;
    }

    /**
     * Demonstrates Beetle as a permitted implementation of the Java 17 sealed Flys interface.
     */
    public String shooBeetle() {
        Beetle beetle = new Beetle();
        String result = "You shoo the beetle. It says: " + beetle.speak()
                + " and it flies away: " + beetle.fly();
        logger.info("Shooing the beetle, it says: {} and flies away: {}", beetle.speak(), beetle.fly());
        return result;
    }

    /**
     * Demonstrates Bird as a permitted implementation of the Java 17 sealed Flys interface.
     */
    public String shooBird() {
        Bird bird = new Bird();
        String result = "You shoo the bird. It says: " + bird.speak() + " and it flies away: " + bird.fly();
        logger.info("Shooing the bird, it says: {} and flies away: {}", bird.speak(), bird.fly());
        return result;
    }

}
