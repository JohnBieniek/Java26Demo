package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.java9.manager.Java9Manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/java9")
@Tag(name = "Java 9 Controller", description = "Endpoints demonstrating Java 9 features.")
public class Java9Controller {
    private final Java9Manager java9Manager;

    public Java9Controller(Java9Manager java9Manager) {
        this.java9Manager = java9Manager;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/moduleDemo")
    @Operation(
        summary = "Demonstrate the Java 9 module system",
        description = "Java9Manager is compiled in the dedicated com.JohnBieniek.Java26Demo.java.nine JPMS module. "
                + "Its module-info.java exports the manager package and opens it to Spring. The response also "
                + "shows whether the current application launch placed the module on the module path."
    )
    public String moduleDemo() {
        return java9Manager.moduleDemo();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/immutableCollectionsDemo")
    @Operation(
        summary = "Demonstrate Java 9 collection factory methods",
        description = "Creates compact unmodifiable collections with List.of, Set.of, and Map.of."
    )
    public String immutableCollectionsDemo() {
        return java9Manager.immutableCollectionsDemo();
    }
}
