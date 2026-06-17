package com.JohnBieniek.Java26Demo.model;

import com.JohnBieniek.Java26Demo.model.interfaces.Flys;

public class Plane implements Flys {
    // The model variable is declared as final, which means that once it is assigned a value, it cannot be changed. 
    // This ensures that the model of the plane remains constant throughout the lifecycle of the Plane object, providing immutability and thread safety.   
    final String model = "Boeing 747";

    @Override
    public String fly() {
        return "Vroom, the " + model + " takes flight!";
    }
}
