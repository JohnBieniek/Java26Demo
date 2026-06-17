package com.JohnBieniek.Java26Demo.model.interfaces;

public interface Flys {
    String fly();

    // A default method in an interface provides a default implementation that can be used by any class that implements the interface. 
    // This allows for backward compatibility and the ability to add new methods to interfaces without breaking existing implementations.
    default String describeFlight() {
        return "This object can fly using a default interface method.";
    }
}
