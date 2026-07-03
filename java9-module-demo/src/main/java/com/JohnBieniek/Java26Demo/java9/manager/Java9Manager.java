package com.JohnBieniek.Java26Demo.java9.manager;

import java.lang.module.ModuleDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Java9Manager {
    private static final String DECLARED_MODULE_NAME = "com.JohnBieniek.Java26Demo.java.nine";

    /**
     * Demonstrates the Java 9 module system. Java9Manager is compiled in a dedicated
     * JPMS module that exports this package.
     */
    public String moduleDemo() {
        Module runtimeModule = Java9Manager.class.getModule();
        ModuleDescriptor descriptor = runtimeModule.getDescriptor();

        return "declared module: " + DECLARED_MODULE_NAME
                + " | runtime module: " + runtimeModule.getName()
                + " | running on module path: " + runtimeModule.isNamed()
                + " | descriptor available at runtime: " + (descriptor != null);
    }

    /**
     * Demonstrates Java 9 immutable collection factory methods.
     */
    public String immutableCollectionsDemo() {
        List<String> animals = List.of("Beetle", "Bird", "Dog");
        Set<String> teams = Set.of("Alpha", "Beta", "Gamma");
        Map<String, Integer> projectBudgets = Map.of(
                "Cloud Migration", 250000,
                "Mobile App", 175000,
                "Sales Dashboard", 90000
        );

        return "List.of: " + animals
                + " | Set.of: " + teams
                + " | Map.of: " + projectBudgets;
    }
}
