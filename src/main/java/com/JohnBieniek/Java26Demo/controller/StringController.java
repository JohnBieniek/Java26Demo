package com.JohnBieniek.Java26Demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/demo")
@Tag(name = "String Semantics Controller", description = "Small demonstrations of Java String identity, value equality, literal interning, immutability, and the behavioral difference between the == operator and String.equals.")
public class StringController {	
    private static final Logger logger = LoggerFactory.getLogger(StringController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/compareIdenticalUniqueStringsWtihDoubleEquals")
    @Operation(
        summary = "Compare distinct String objects with ==",
        description = "Constructs two separate String objects containing the same characters and compares their references with ==. The result is false because identity comparison does not inspect character content."
    )
    public Boolean compareIdenticalUniqueStringsWtihDoubleEquals(){
        return new String("a") == new String("a"); // false;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/compareIdenticalUniqueStringsWtihDotEquals")
    @Operation(
        summary = "Compare distinct String objects with equals",
        description = "Constructs two separate String objects containing the same characters and compares their values with String.equals. The result is true because equals compares character content."
    )
    public Boolean compareIdenticalUniqueStringsWtihDotEquals(){
        return new String("a").equals(new String("a")); // true;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/compareIdenticalStringLitteralsWtihDoubleEquals")
    @Operation(
        summary = "Compare interned String literals with ==",
        description = "Assigns the same literal to two variables and compares their references. The result is true because identical literals share the string-pool instance; converting one value to uppercase also demonstrates that Strings are immutable."
    )
    public Boolean compareIdenticalStringLitteralsWtihDoubleEquals(){
        String a = "hello";
        String b = "hello";
        String c = b.toUpperCase();
        logger.info("String b is immutable, so when we call toUpperCase on it, it creates a new String object with the value " + c + ", but the original String b still references the same memory location as a");
        return a == b; // true;
    }
}
