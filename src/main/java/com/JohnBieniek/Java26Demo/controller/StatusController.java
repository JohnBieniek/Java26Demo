package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "Status Controller", description = "Endpoints for checking the status of the application and its various services.")
public class StatusController {
    @RequestMapping(method = RequestMethod.GET, path = "/health")
    @Operation(
        summary = "Check the health of the application",
        description = "Returns the health status of the application and its various services."
    )
    public String health() {
        return "Application is healthy";
    }
}
