package com.JohnBieniek.Java26Demo.utility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Java26FeatureUtilsTests {
    @Test
    void scalarAndVectorHelpersProduceTheSameResult() {
        int[] left = {1, 2, 3, 4, 5};
        int[] right = {10, 20, 30, 40, 50};

        assertThat(Java26FeatureUtils.addWithVectors(left, right))
                .containsExactly(Java26FeatureUtils.addWithScalarLoop(left, right));
    }

    @Test
    void identifiesAotRelatedJvmArguments() {
        assertThat(Java26FeatureUtils.isAotRelatedArgument("-XX:AOTCache=app.aot")).isTrue();
        assertThat(Java26FeatureUtils.isAotRelatedArgument("-Xmx512m")).isFalse();
    }
}
