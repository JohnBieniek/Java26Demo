package com.JohnBieniek.Java26Demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.model.Plane;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/demoInterface")
    @Operation(
        summary = "Demonstrate a Java interface",
        description = "Creates a Plane and calls the fly method defined by its interface."
    )
    public String demoInterface() {
        Plane plane = new Plane();
        String result = "You launch the plane.: " + plane.fly();
        logger.info("Launching the plane,: {}", plane.fly());
        return result;
    }
}
