package com.JohnBieniek.Java26Demo.manager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AnimalManagerTests {
    @Test
    void demoInterfaceUsesBeetleFlightBehavior() {
        assertThat(new AnimalManager().demoInterface())
                .startsWith("The beetle takes flight: ");
    }
}
