package com.JohnBieniek.Java26Demo.utility;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

/**
 * Reusable support operations shared by the Java 26 feature demonstrations.
 *
 * This class contains extracted subroutines rather than endpoint orchestration.
 */
public final class Java26FeatureUtils {
    private static final VectorSpecies<Integer> INT_SPECIES = IntVector.SPECIES_PREFERRED;

    private Java26FeatureUtils() {}

    /** Holds the state used to observe one-time LazyConstant initialization. */
    public static final class LazyValueDemo {
        private final AtomicInteger computationCount = new AtomicInteger();
        private final LazyConstant<String> value = LazyConstant.of(() -> {
            computationCount.incrementAndGet();
            return "Java 26 lazy constant value";
        });

        /**
         * Reports whether the lazy constant has already been computed.
         *
         * @return true after initialization
         */
        public boolean isInitialized() {
            return value.isInitialized();
        }

        /**
         * Retrieves the constant, computing it on the first call.
         *
         * @return constant value
         */
        public String get() {
            return value.get();
        }

        /**
         * Returns the number of supplier invocations.
         *
         * @return computation count
         */
        public int computationCount() {
            return computationCount.get();
        }
    }

    /** Mutable test subject whose field is declared final for the reflection demo. */
    public static final class FinalFieldHolder {
        private final String value;

        /**
         * Creates a holder with an immutable field value.
         *
         * @param value initial value
         */
        public FinalFieldHolder(String value) {
            this.value = value;
        }

        /**
         * Reads the final field normally.
         *
         * @return current field value
         */
        public String value() {
            return value;
        }
    }

    /**
     * Simulates a blocking remote service lookup.
     *
     * @param service service label
     * @param result simulated result
     * @return labeled lookup result
     * @throws InterruptedException when the simulated wait is interrupted
     */
    public static String fakeServiceCall(String service, String result) throws InterruptedException {
        Thread.sleep(50);
        return service + " lookup: " + result;
    }

    /**
     * Creates the consistent failure payload used by structured operations.
     *
     * @param message failure detail
     * @return ordered failure response
     */
    public static Map<String, Object> failure(String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "failure");
        result.put("error", message);
        return result;
    }

    /**
     * Adds equally sized arrays with a scalar loop.
     *
     * @param left left operands
     * @param right right operands
     * @return element-wise sums
     */
    public static int[] addWithScalarLoop(int[] left, int[] right) {
        requireSameLength(left, right);
        int[] result = new int[left.length];
        for (int index = 0; index < left.length; index++) {
            result[index] = left[index] + right[index];
        }
        return result;
    }

    /**
     * Adds equally sized arrays with preferred-width vectors and a scalar tail.
     *
     * @param left left operands
     * @param right right operands
     * @return element-wise sums
     */
    public static int[] addWithVectors(int[] left, int[] right) {
        requireSameLength(left, right);
        int[] result = new int[left.length];
        int index = 0;
        int upperBound = INT_SPECIES.loopBound(left.length);
        for (; index < upperBound; index += INT_SPECIES.length()) {
            IntVector leftVector = IntVector.fromArray(INT_SPECIES, left, index);
            IntVector rightVector = IntVector.fromArray(INT_SPECIES, right, index);
            leftVector.add(rightVector).intoArray(result, index);
        }
        for (; index < left.length; index++) {
            result[index] = left[index] + right[index];
        }
        return result;
    }

    /**
     * Returns the runtime's preferred species for integer vector operations.
     *
     * @return preferred integer vector species
     */
    public static VectorSpecies<Integer> preferredIntSpecies() {
        return INT_SPECIES;
    }

    /**
     * Identifies JVM arguments related to AOT or shared archives.
     *
     * @param argument JVM input argument
     * @return whether the argument is AOT/archive related
     */
    public static boolean isAotRelatedArgument(String argument) {
        String normalized = argument.toLowerCase();
        return normalized.contains("aot")
                || normalized.contains("archive")
                || normalized.contains("shared");
    }

    /** Validates the common precondition for element-wise array operations. */
    private static void requireSameLength(int[] left, int[] right) {
        if (left.length != right.length) {
            throw new IllegalArgumentException("Input arrays must have the same length.");
        }
    }
}
