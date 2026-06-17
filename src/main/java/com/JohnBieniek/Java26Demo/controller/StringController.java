package com.JohnBieniek.Java26Demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class StringController {	
    private static final Logger logger = LoggerFactory.getLogger(StringController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/compareIdenticalUniqueStringsWtihDoubleEquals")
    public Boolean compareIdenticalUniqueStringsWtihDoubleEquals(){
        return new String("a") == new String("a"); // false;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/compareIdenticalUniqueStringsWtihDotEquals")
    public Boolean compareIdenticalUniqueStringsWtihDotEquals(){
        return new String("a").equals(new String("a")); // true;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/compareIdenticalStringLitteralsWtihDoubleEquals")
    public Boolean compareIdenticalStringLitteralsWtihDoubleEquals(){
        String a = "hello";
        String b = "hello";
        String c = b.toUpperCase();
        logger.info("String b is immutable, so when we call toUpperCase on it, it creates a new String object with the value " + c + ", but the original String b still references the same memory location as a");
        return a == b; // true;
    }
}