package com.JohnBieniek.Java26Demo.manager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class Java9Manager {

    /**
     * Demonstrates the Java 9 module runtime API. This project does not declare
     * module-info.java, so it runs in the unnamed module.
     */
    public String moduleDemo() {
        Module module = Java9Manager.class.getModule();
        return "module name: " + module.getName()
                + " | named module: " + module.isNamed()
                + " | package count: " + module.getPackages().size();
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
