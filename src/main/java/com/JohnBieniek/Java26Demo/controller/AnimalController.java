package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.service.AnimalService;
import com.JohnBieniek.Java26Demo.model.animal.AnimalType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/animals")
@Tag(name = "Animal Controller", description = "Animal-themed demonstrations of interfaces, abstract base classes, polymorphism, method overriding, static method hiding, switch expressions, pattern matching, text blocks, and sealed types across modern Java releases.")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoInterface")
    @Operation(
        summary = "Demonstrate a Java interface",
        description = "Creates a Beetle through the animal service and calls the fly method defined by its sealed interface."
    )
    public String demoInterface() {
        return animalService.demoInterface();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/animalGuide")
    @Operation(
        summary = "Demonstrate a Java 15 text block",
        description = "Returns formatted multi-line text using a text block. Text blocks became permanent "
                + "in Java 15 and avoid manually concatenating strings or escaping newline characters."
    )
    public String animalGuide() {
        return animalService.animalGuide();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/describeAnimal")
    @Operation(
        summary = "Demonstrate a Java 14 switch expression",
        description = "Switch expressions became a permanent feature in Java 14. "
                + "Unlike an old switch statement, this switch directly returns a value, uses arrow cases "
                + "that do not fall through, and requires every enum value to be handled."
    )
    public String describeAnimal(@RequestParam AnimalType animalType) {
        return animalService.describeAnimal(animalType);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedBeetle")
    @Operation(
        summary = "Feed a Beetle through its Animal base type",
        description = "Creates a Beetle referenced as Animal and invokes its eat implementation, demonstrating runtime polymorphism through an abstract base type."
    )
    public String feedBeetle() {
        return animalService.feedBeetle();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedBird")
    @Operation(
        summary = "Feed a Bird through its Animal base type",
        description = "Creates a Bird referenced as Animal and invokes its eat implementation, showing that the concrete override is selected at runtime."
    )
    public String feedBird() {
        return animalService.feedBird();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feedDog")
    @Operation(
        summary = "Feed a Dog through its Animal base type",
        description = "Creates a Dog referenced as Animal and invokes its eat implementation to demonstrate subtype behavior through a common abstraction."
    )
    public String feedDog() {
        return animalService.feedDog();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/identifyAnimal")
    @Operation(
        summary = "Demonstrate Java 16 pattern matching for instanceof",
        description = "Pattern matching for instanceof became permanent in Java 16. It tests the Animal subtype "
                + "and binds a correctly typed Beetle, Bird, or Dog variable without requiring a separate cast."
    )
    public String identifyAnimal(@RequestParam AnimalType animalType) {
        return animalService.identifyAnimal(animalType);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/petDog")
    @Operation(
        summary = "Invoke an overridden Dog method",
        description = "Creates a Dog and calls its speak implementation, demonstrating ordinary method overriding and dynamic dispatch."
    )
    public String petDog() {
        return animalService.petDog();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateBeetle")
    @Operation(
        summary = "Call inherited static sleep behavior for Beetle",
        description = "Calls Beetle.sleep to demonstrate access to static behavior inherited from the Animal hierarchy."
    )
    public String sedateBeetle() {
        return animalService.sedateBeetle();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateBird")
    @Operation(
        summary = "Call inherited static sleep behavior for Bird",
        description = "Calls Bird.sleep to demonstrate access to static behavior inherited from the Animal hierarchy."
    )
    public String sedateBird() {
        return animalService.sedateBird();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sedateDog")
    @Operation(
        summary = "Demonstrate static method hiding with Dog",
        description = "Calls Dog.sleep, whose declaration hides the Animal static method, illustrating that static dispatch follows the referenced class rather than runtime polymorphism."
    )
    public String sedateDog() {
        return animalService.sedateDog();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/shooBeetle")
    @Operation(
        summary = "Demonstrate a sealed interface with Beetle",
        description = "Sealed classes and interfaces became a permanent feature in Java 17. "
                + "Beetle implements the sealed Flys interface, which permits only Beetle and Bird implementations."
    )
    public String shooBeetle() {
        return animalService.shooBeetle();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/shooBird")
    @Operation(
        summary = "Demonstrate a sealed interface with Bird",
        description = "Sealed classes and interfaces became a permanent feature in Java 17. "
                + "Bird implements the sealed Flys interface, which permits only Beetle and Bird implementations."
    )
    public String shooBird() {
        return animalService.shooBird();
    }

}
