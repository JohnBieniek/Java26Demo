package com.JohnBieniek.Java26Demo.model;

import com.JohnBieniek.Java26Demo.model.interfaces.Animal;

public class Dog implements Animal {
    @Override
    public String speak() {
        return "Woof!";
    }
    
}
