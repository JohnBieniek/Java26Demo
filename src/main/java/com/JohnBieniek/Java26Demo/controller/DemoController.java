package com.JohnBieniek.Java26Demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.model.animal.Beetle;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/demoInterface")
    @Operation(
        summary = "Demonstrate a Java interface",
        description = "Creates a Beetle and calls the fly method defined by its sealed interface."
    )
    public String demoInterface() {
        Beetle beetle = new Beetle();
        String result = "The beetle takes flight: " + beetle.fly();
        logger.info("The beetle takes flight: {}", beetle.fly());
        return result;
    }
}
