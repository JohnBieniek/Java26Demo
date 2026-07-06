package com.JohnBieniek.Java26Demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Java10Service {

    /**
     * Demonstrates Java 10 local variable type inference with var.
     */
    public String localVariableTypeInferenceDemo() {
        var animals = List.of("Beetle", "Bird", "Dog");
        var firstAnimal = animals.get(0);
        var animalCount = animals.size();

        return "first animal: " + firstAnimal
                + " | inferred list type: " + animals.getClass().getSimpleName()
                + " | animal count: " + animalCount;
    }
}
