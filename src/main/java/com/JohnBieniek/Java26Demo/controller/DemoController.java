package com.JohnBieniek.Java26Demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.model.Dog;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {	
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String test(String input){
        String result = input == null ? "No input" : input + " complete";
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/petDog")
    public String petDog(){
        Dog dog = new Dog();
        String result = "You pet the dog. It says: " + dog.speak();
        logger.info("Petting the dog, it says: {}", dog.speak());
        return result;
    }
}