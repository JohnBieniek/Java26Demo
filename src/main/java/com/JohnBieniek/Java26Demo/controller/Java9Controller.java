package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.manager.Java9ModuleManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/java9")
@Tag(name = "Java 9 Controller", description = "Endpoints demonstrating Java 9 features.")
public class Java9Controller {
    private final Java9ModuleManager java9ModuleManager;

    public Java9Controller(Java9ModuleManager java9ModuleManager) {
        this.java9ModuleManager = java9ModuleManager;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/moduleDemo")
    @Operation(
        summary = "Run a real Java 9 named module",
        description = "Launches the dedicated com.JohnBieniek.Java26Demo.java.nine module with "
                + "--module-path and --module, then reports its runtime module name and named-module status."
    )
    public String moduleDemo() {
        return java9ModuleManager.moduleDemo();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/immutableCollectionsDemo")
    @Operation(
        summary = "Demonstrate Java 9 collection factory methods",
        description = "Uses the manager compiled in the Java 9 module to create compact unmodifiable "
                + "collections with List.of, Set.of, and Map.of."
    )
    public String immutableCollectionsDemo() {
        return java9ModuleManager.immutableCollectionsDemo();
    }
}
