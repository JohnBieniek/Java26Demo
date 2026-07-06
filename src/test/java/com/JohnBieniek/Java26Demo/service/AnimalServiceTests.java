package com.JohnBieniek.Java26Demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AnimalServiceTests {
    @Test
    void demoInterfaceUsesBeetleFlightBehavior() {
        assertThat(new AnimalService().demoInterface())
                .startsWith("The beetle takes flight: ");
    }
}
