package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.manager.AnimalManager;
import com.JohnBieniek.Java26Demo.model.animal.AnimalType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/animals")
@Tag(name = "Animal Controller", description = "Endpoints for demoing interfaces, abstract classes, and final declarations with animals.")
public class AnimalController {
    private final AnimalManager animalManager;

    public AnimalController(AnimalManager animalManager) {
        this.animalManager = animalManager;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/animalGuide")
    @Operation(
        summary = "Demonstrate a Java 15 text block",
        description = "Returns formatted multi-line text using a text block. Text blocks became permanent "
                + "in Java 15 and avoid manually concatenating strings or escaping newline characters."
    )
    public String animalGuide() {
        return animalManager.animalGuide();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/describeAnimal")
    @Operation(
        summary = "Demonstrate a Java 14 switch expression",
        description = "Switch expressions became a permanent feature in Java 14. "
                + "Unlike an old switch statement, this switch directly returns a value, uses arrow cases "
                + "that do not fall through, and requires every enum value to be handled."
    )
    public String describeAnimal(@RequestParam AnimalType animalType) {
        return animalManager.describeAnimal(animalType);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedBeetle")
    public String feedBeetle() {
        return animalManager.feedBeetle();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedBird")
    public String feedBird() {
        return animalManager.feedBird();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedDog")
    public String feedDog() {
        return animalManager.feedDog();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/identifyAnimal")
    @Operation(
        summary = "Demonstrate Java 16 pattern matching for instanceof",
        description = "Pattern matching for instanceof became permanent in Java 16. It tests the Animal subtype "
                + "and binds a correctly typed Beetle, Bird, or Dog variable without requiring a separate cast."
    )
    public String identifyAnimal(@RequestParam AnimalType animalType) {
        return animalManager.identifyAnimal(animalType);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/petDog")
    public String petDog() {
        return animalManager.petDog();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateBeetle")
    public String sedateBeetle() {
        return animalManager.sedateBeetle();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateBird")
    public String sedateBird() {
        return animalManager.sedateBird();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateDog")
    public String sedateDog() {
        return animalManager.sedateDog();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/shooBeetle")
    @Operation(
        summary = "Demonstrate a sealed interface with Beetle",
        description = "Sealed classes and interfaces became a permanent feature in Java 17. "
                + "Beetle implements the sealed Flys interface, which permits only Beetle and Bird implementations."
    )
    public String shooBeetle() {
        return animalManager.shooBeetle();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/shooBird")
    @Operation(
        summary = "Demonstrate a sealed interface with Bird",
        description = "Sealed classes and interfaces became a permanent feature in Java 17. "
                + "Bird implements the sealed Flys interface, which permits only Beetle and Bird implementations."
    )
    public String shooBird() {
        return animalManager.shooBird();
    }

}
