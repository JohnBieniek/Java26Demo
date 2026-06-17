package com.JohnBieniek.Java26Demo.model;

import com.JohnBieniek.Java26Demo.model.abstracts.Animal;

// The Dog class is declared as final, which means it cannot be subclassed. This is useful when you want to prevent other classes from inheriting from Dog, ensuring that its behavior remains unchanged and secure.
public final class Dog extends Animal {
    @Override
    public String speak() {
        return "Woof!";
    }
    
    //Method overloading example
    public String speak(String words) {
        return "Woof! Also, " + words;
    }
    
    //Method hiding occurs when a static method in a subclass has the same signature as a static method in the superclass. The subclass's method hides the superclass's method.
    public static String sleep() {
        return "The dog curls up and sleeps.";
    }
}
