package com.JohnBieniek.Java26Demo.model.animal.abstracts;

public abstract class Animal {
    public String speak() {
        return "Animal sound";
    }

    public static String sleep() {
        return "Zzz...";
    }

    // Final keeps eating behavior consistent across every animal subtype.
    public final String eat() {
        return "The animal eats.";
    }
}
