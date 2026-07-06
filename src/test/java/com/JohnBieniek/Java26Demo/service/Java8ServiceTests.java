package com.JohnBieniek.Java26Demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class Java8ServiceTests {
    private final Java8Service service = new Java8Service();

    @Test
    void methodReferenceConvertsNamesToUppercase() {
        assertThat(service.demoMethodReference()).isEqualTo("ALICE, BOB, CHARLIE");
    }

    @Test
    void optionalDefaultIsOwnedByService() {
        assertThat(service.testOptional(Optional.empty())).isEqualTo("No input complete");
    }
}
