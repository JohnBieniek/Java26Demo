package com.JohnBieniek.Java26Demo.dto.organization;

public record ProjectSummary(
        Long id,
        String name,
        int budget,
        String teamName) {
}
