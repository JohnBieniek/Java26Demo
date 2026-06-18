package com.JohnBieniek.Java26Demo.model.animal;

import com.JohnBieniek.Java26Demo.model.animal.abstracts.Animal;

public final class Bird extends Animal implements Flys {
    @Override
    public String speak() {
        return "Tweet!";
    }

    @Override
    public String fly() {
        return "Whoosh, the bird flies!" + speak();
    }
}
