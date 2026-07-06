package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.service.Java11Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/java11")
@Tag(name = "Java 11 Controller", description = "Runnable Java 11 demonstrations covering the standardized HTTP Client, String whitespace and line-processing methods, and Optional.isEmpty with deterministic local behavior and optional inputs.")
public class Java11Controller {
    private final Java11Service java11Service;

    public Java11Controller(Java11Service java11Service) {
        this.java11Service = java11Service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/httpClientDemo")
    @Operation(
        summary = "Demonstrate the Java 11 HTTP client",
        description = "Starts a temporary loopback HTTP server and calls it with HttpClient, HttpRequest, "
                + "and HttpResponse. The demo is deterministic and does not require internet access."
    )
    public String httpClientDemo() {
        return java11Service.httpClientDemo();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/stringMethodsDemo")
    @Operation(
        summary = "Demonstrate Java 11 String methods",
        description = "Uses String.isBlank, String.lines, and String.strip. A multiline default value is used "
                + "when input is omitted."
    )
    public String stringMethodsDemo(@RequestParam(required = false) String input) {
        return java11Service.stringMethodsDemo(input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/optionalIsEmptyDemo")
    @Operation(
        summary = "Demonstrate Java 11 Optional.isEmpty",
        description = "Builds an Optional from the input and uses isEmpty to distinguish missing or blank values."
    )
    public String optionalIsEmptyDemo(@RequestParam(required = false) String value) {
        return java11Service.optionalIsEmptyDemo(value);
    }
}
