package com.JohnBieniek.Java26Demo.manager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class Java8ManagerTests {
    private final Java8Manager manager = new Java8Manager();

    @Test
    void methodReferenceConvertsNamesToUppercase() {
        assertThat(manager.demoMethodReference()).isEqualTo("ALICE, BOB, CHARLIE");
    }

    @Test
    void optionalDefaultIsOwnedByManager() {
        assertThat(manager.testOptional(Optional.empty())).isEqualTo("No input complete");
    }
}
