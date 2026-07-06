package com.JohnBieniek.Java26Demo.manager;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.JohnBieniek.Java26Demo.client.Java26Client;

class Java26ManagerTests {
    @Test
    void managerRunsTheJava26FeatureDemos() {
        try (Java26Client client = new Java26Client()) {
            Java26Manager manager = new Java26Manager(client);
            assertThat(manager.primitiveSwitchDemo(42)).isEqualTo("fits exactly in int: 42");
            assertThat(manager.primitiveSwitchDemo((long) Integer.MAX_VALUE + 1))
                    .isEqualTo("long outside int range: 2147483648");
            assertThat(manager.lazyConstantDemo())
                    .contains("computation count=1")
                    .contains("Second result, same value: true");
            assertThat(manager.structuredConcurrencyDemo())
                    .containsEntry("status", "success")
                    .containsKeys("profile", "orders", "recommendations");
            assertThat(manager.pemDemo())
                    .contains("Decoded algorithm: RSA")
                    .contains("Decoded key matches original: true");
            assertThat(manager.vectorApiDemo())
                    .containsEntry("resultsMatch", true)
                    .containsEntry("arrayLength", 1_024);
            assertThat(manager.aotInfo())
                    .containsKeys("javaVersion", "vmName", "garbageCollectors", "aotRelatedJvmArguments");
            assertThat(manager.appletRemovalDemo())
                    .contains("Java 26 removed the Applet API");
        }
    }
}
