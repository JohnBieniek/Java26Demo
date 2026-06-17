package com.JohnBieniek.Java26Demo.model;

import com.JohnBieniek.Java26Demo.model.abstracts.Animal;
import com.JohnBieniek.Java26Demo.model.interfaces.Flys;

public class Bird extends Animal implements Flys {
    @Override
    public String speak() {
        return "Tweet!";
    }
    
    @Override
    public String fly() {
        return "Whoosh, the bird flies!" + speak();
    }
}
