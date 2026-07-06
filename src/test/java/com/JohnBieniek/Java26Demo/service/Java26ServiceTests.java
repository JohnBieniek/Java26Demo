package com.JohnBieniek.Java26Demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.JohnBieniek.Java26Demo.client.Java26Client;

class Java26ServiceTests {
    @Test
    void managerRunsTheJava26FeatureDemos() {
        try (Java26Client client = new Java26Client()) {
            Java26Service service = new Java26Service(client);
            assertThat(service.primitiveSwitchDemo(42)).isEqualTo("fits exactly in int: 42");
            assertThat(service.primitiveSwitchDemo((long) Integer.MAX_VALUE + 1))
                    .isEqualTo("long outside int range: 2147483648");
            assertThat(service.lazyConstantDemo())
                    .contains("computation count=1")
                    .contains("Second result, same value: true");
            assertThat(service.structuredConcurrencyDemo())
                    .containsEntry("status", "success")
                    .containsKeys("profile", "orders", "recommendations");
            assertThat(service.pemDemo())
                    .contains("Decoded algorithm: RSA")
                    .contains("Decoded key matches original: true");
            assertThat(service.vectorApiDemo())
                    .containsEntry("resultsMatch", true)
                    .containsEntry("arrayLength", 1_024);
            assertThat(service.aotInfo())
                    .containsKeys("javaVersion", "vmName", "garbageCollectors", "aotRelatedJvmArguments");
            assertThat(service.appletRemovalDemo())
                    .contains("Java 26 removed the Applet API");
        }
    }
}
