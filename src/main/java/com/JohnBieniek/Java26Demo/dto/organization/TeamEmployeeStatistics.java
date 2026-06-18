package com.JohnBieniek.Java26Demo.dto.organization;

import java.math.BigDecimal;

public record TeamEmployeeStatistics(
        String teamName,
        long employeeCount,
        BigDecimal totalCompensation,
        BigDecimal averageCompensation,
        int lowestSalary,
        int highestSalary) {
}
