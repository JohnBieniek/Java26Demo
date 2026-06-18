package com.JohnBieniek.Java26Demo.model.animal;

public sealed interface Flys permits Beetle, Bird {
    String fly();

    default String describeFlight() {
        return "This animal can fly using a default interface method.";
    }
}
