package com.JohnBieniek.Java26Demo.dto.organization;

public record EmployeeProjectAssignment(
        EmployeeSummary employee,
        ProjectSummary project) {
}
