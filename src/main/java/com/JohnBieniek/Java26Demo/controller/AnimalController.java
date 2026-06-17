package com.JohnBieniek.Java26Demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.model.Bird;
import com.JohnBieniek.Java26Demo.model.Dog;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/animals")
@Tag(name = "Animal Management", description = "Endpoints for demoing interfaces, abstract classes, and final declarations with animals.")
public class AnimalController {
    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/feedBird")
    public String feedBird() {
        Bird bird = new Bird();
        String result = "You feed the bird." + bird.eat();
        logger.info("You feed the bird,: {}", bird.eat());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedDog")
    public String feedDog() {
        Dog dog = new Dog();
        String result = "You feed the dog." + dog.eat();
        logger.info("You feed the dog,: {}", dog.eat());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/petDog")
    public String petDog() {
        Dog dog = new Dog();
        String result = "You pet the dog. It says: " + dog.speak();
        logger.info("Petting the dog, it says: {}", dog.speak());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateBird")
    public String sedateBird() {
        String result = "You sedate a bird." + Bird.sleep();
        logger.info("You sedate a bird,", Bird.sleep());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateDog")
    public String sedateDog() {
        String result = "You sedate a dog." + Dog.sleep();
        logger.info("You sedate a dog,", Dog.sleep());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/shooBird")
    public String shooBird() {
        Bird bird = new Bird();
        String result = "You shoo the bird. It says: " + bird.speak() + " and it flies away: " + bird.fly();
        logger.info("Shooing the bird, it says: {} and flies away: {}", bird.speak(), bird.fly());
        return result;
    }
}
