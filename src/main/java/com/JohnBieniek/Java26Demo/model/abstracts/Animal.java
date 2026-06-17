package com.JohnBieniek.Java26Demo.model.abstracts;

public abstract class Animal {
    public String speak(){
        return "Animal sound";
    }

    public static String sleep(){
        return "Zzz...";
    }

    // The eat method is declared as final, which means that it cannot be overridden by any subclass of Animal. This ensures that all animals will have the same implementation of the eat method, providing a consistent behavior.
    public final String eat(){
        return "The animal eats.";
    }
}
