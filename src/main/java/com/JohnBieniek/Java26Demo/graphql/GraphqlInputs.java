package com.JohnBieniek.Java26Demo.graphql;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class GraphqlInputs {
    private GraphqlInputs() {
    }

    public record CreateTeam(@NotBlank String name, @NotBlank String location) {
    }

    public record CreateEmployee(
            @NotBlank String name,
            @NotBlank String department,
            @NotNull @Min(0) Integer salary,
            @NotNull @Min(0) Integer bonus,
            String phoneNumber,
            @NotNull Long teamId) {
    }

    public record CreateProject(@NotBlank String name, @NotNull @Min(0) Integer budget, @NotNull Long teamId) {
    }
}
