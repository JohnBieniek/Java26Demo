package com.JohnBieniek.Java26Demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    // private final Java9Manager java9Manager;

    // public Java9Controller(Java9Manager java9Manager) {
    //     this.java9Manager = java9Manager;
    // }

    // @RequestMapping(method = RequestMethod.GET, path = "/moduleDemo")
    // @Operation(
    //     summary = "Demonstrate the Java 9 module system",
    //     description = "Runs the dedicated Java 9 module runner in a separate JVM so the response shows the actual runtime module information."
    // )
    // public String moduleDemo() {
    //     try {
    //         String javaHome = System.getProperty("java.home");
    //         String javaExecutable = javaHome + "/bin/java";

    //         List<String> command = new ArrayList<>();
    //         command.add(javaExecutable);
    //         command.add("--module-path");
    //         command.add("java9-module-demo/build/classes/java/main");
    //         command.add("--module");
    //         command.add("com.JohnBieniek.Java26Demo.java.nine/com.JohnBieniek.Java26Demo.java9.manager.Java9ModuleDemoRunner");

    //         Process process = new ProcessBuilder(command)
    //                 .redirectErrorStream(true)
    //                 .start();

    //         try (BufferedReader reader = new BufferedReader(
    //                 new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
    //             String output = reader.lines().collect(Collectors.joining(System.lineSeparator()));
    //             int exitCode = process.waitFor();
    //             if (exitCode != 0) {
    //                 return "Module runner failed with exit code " + exitCode + ". Output:\n" + output;
    //             }
    //             return output;
    //         }
    //     } catch (IOException | InterruptedException ex) {
    //         Thread.currentThread().interrupt();
    //         return "Failed to run module demo: " + ex.getMessage();
    //     }
    // }

    // @RequestMapping(method = RequestMethod.GET, path = "/immutableCollectionsDemo")
    // @Operation(
    //     summary = "Demonstrate Java 9 collection factory methods",
    //     description = "Creates compact unmodifiable collections with List.of, Set.of, and Map.of."
    // )
    // public String immutableCollectionsDemo() {
    //     return java9Manager.immutableCollectionsDemo();
    // }
}
