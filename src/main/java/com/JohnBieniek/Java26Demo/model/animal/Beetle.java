package com.JohnBieniek.Java26Demo.model.animal;

import com.JohnBieniek.Java26Demo.model.animal.abstracts.Animal;

public final class Beetle extends Animal implements Flys {
    @Override
    public String speak() {
        return "Buzz!";
    }

    @Override
    public String fly() {
        return "The beetle opens its wings and flies.";
    }
}
