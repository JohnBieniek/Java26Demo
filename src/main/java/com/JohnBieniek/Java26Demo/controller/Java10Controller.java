package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.service.Java10Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/java10")
@Tag(name = "Java 10 Controller", description = "A focused demonstration of Java 10 local-variable type inference with var, showing inferred local types while preserving Java's compile-time static typing.")
public class Java10Controller {
    private final Java10Service java10Service;

    public Java10Controller(Java10Service java10Service) {
        this.java10Service = java10Service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/localVariableTypeInferenceDemo")
    @Operation(
        summary = "Demonstrate Java 10 local variable type inference",
        description = "Uses var for local variables while Java still determines and enforces their static types at compile time."
    )
    public String localVariableTypeInferenceDemo() {
        return java10Service.localVariableTypeInferenceDemo();
    }
}
