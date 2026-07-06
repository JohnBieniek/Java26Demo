package com.JohnBieniek.Java26Demo.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.dto.java21.LargeInputProcessingResult;
import com.JohnBieniek.Java26Demo.dto.java21.SequencedCollectionResult;
import com.JohnBieniek.Java26Demo.service.Java21Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/java21")
@Tag(name = "Java 21 Controller", description = "Demonstrations of permanent Java 21 features: nested record patterns with guarded switch cases, virtual-thread-per-task processing of blocking workloads, and the unified SequencedCollection encounter-order API.")
public class Java21Controller {
    private final Java21Service java21Service;

    public Java21Controller(Java21Service java21Service) {
        this.java21Service = java21Service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getEmployeeProjectAssignmentDescription")
    @Operation(
        summary = "Destructure nested records with a Java 21 record pattern using Java 21 guarded pattern switch",
        description = "Looks up a real employee/project assignment and switches on its nested record structure. "
                + "The EmployeeProjectAssignment record pattern opens the assignment into its EmployeeSummary "
                + "and ProjectSummary records, then extracts values such as employeeName, department, projectName, "
                + "and budget directly into case variables. A guarded case uses 'when budget >= 200000' to classify "
                + "major projects, while the second case handles all other matching assignments."
    )
    public String getEmployeeProjectAssignmentDescription(
            @RequestParam @NotNull Long employeeId,
            @RequestParam @NotNull Long projectId) {
        return java21Service.getEmployeeProjectAssignmentDescription(employeeId, projectId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/processLargeInput")
    @Operation(
        summary = "Process large input with Java 21 virtual threads",
        description = "Accepts a large plain-text request body, divides it into 10000-character segments, "
                + "runs a simulated blocking validation and word count for each segment using a "
                + "virtual-thread-per-task executor, and returns combined execution statistics. "
                + "The blocking step demonstrates the workload virtual threads are designed to scale. "
                + "When the request body is missing or blank, repeated Lorem Ipsum text is used."
    )
    public LargeInputProcessingResult processLargeInput(@RequestBody(required = false) String input) {
        return java21Service.processLargeInput(input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sequencedCollectionDemo")
    @Operation(
        summary = "Demonstrate Java 21 sequenced collections",
        description = "Java 21 introduced SequencedCollection to provide a uniform encounter order API. "
                + "This example adds elements with addFirst and addLast, reads them with getFirst and getLast, "
                + "and returns both the encounter order and the reversed view. When no values are supplied, "
                + "a default Bird, Dog, Beetle sequence is used."
    )
    public SequencedCollectionResult sequencedCollectionDemo(
            @RequestParam(required = false) List<String> values) {
        return java21Service.sequencedCollectionDemo(values);
    }
}
