package com.JohnBieniek.Java26Demo.model.animal;

import com.JohnBieniek.Java26Demo.model.animal.abstracts.Animal;

// Final prevents Dog behavior from being changed through subclassing.
public final class Dog extends Animal {
    @Override
    public String speak() {
        return "Woof!";
    }

    public String speak(String words) {
        return "Woof! Also, " + words;
    }

    public static String sleep() {
        return "The dog curls up and sleeps.";
    }
}
