package com.JohnBieniek.Java26Demo.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.service.Java8Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/demo")
@Tag(name = "Java 8 Controller", description = "Focused demonstrations of Java 8 lambdas, method references, default interface methods, streams, reductions, forEach processing, and Optional fallback behavior.")
public class Java8Controller {
    private final Java8Service java8Service;

    public Java8Controller(Java8Service java8Service) {
        this.java8Service = java8Service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoLambda")
    @Operation(
            summary = "Transform input with a Java 8 lambda",
            description = "Passes the supplied string to a Function lambda that duplicates the value with an ampersand separator and returns the transformed result.")
    public String demoLambdaStringDoubling(String input) {
        return java8Service.demoLambdaStringDoubling(input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoForEachLambda")
    @Operation(
            summary = "Process a collection with forEach and a lambda",
            description = "Iterates over a demonstration list with Iterable.forEach, appends each name to a shared result, and returns the completed encounter order.")
    public String demoForEachLambda() {
        return java8Service.demoForEachLambda();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoDefaultInterfaceMethod")
    @Operation(
            summary = "Invoke a Java 8 default interface method",
            description = "Creates a Beetle and combines behavior supplied by a default interface method with the class's concrete fly implementation.")
    public String demoDefaultInterfaceMethod() {
        return java8Service.demoDefaultInterfaceMethod();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoMethodReference")
    @Operation(
            summary = "Map stream values with a method reference",
            description = "Uses String::toUpperCase instead of an equivalent lambda, then reduces the uppercase names into one comma-separated result.")
    public String demoMethodReference() {
        return java8Service.demoMethodReference();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoStreamFilterAndReduce")
    @Operation(
            summary = "Filter, map, and reduce a Java 8 stream",
            description = "Filters names that begin with a vowel, maps them to uppercase, and reduces the resulting stream into one ampersand-separated string.")
    public String demoStreamFilterAndReduce() {
        return java8Service.demoStreamFilterAndReduce();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/testOptional")
    @Operation(
            summary = "Use Optional to provide a fallback value",
            description = "Returns the supplied optional string when present or a service-owned default when empty, demonstrating Optional.orElse without null branching in the controller.")
    public String testOptional(Optional<String> input) {
        return java8Service.testOptional(input);
    }
}
