package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.manager.Java9Manager;

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
        description = "Uses the Module runtime API to inspect the module containing Java9Manager. "
                + "Because this project has no module-info.java, it reports the unnamed module."
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
