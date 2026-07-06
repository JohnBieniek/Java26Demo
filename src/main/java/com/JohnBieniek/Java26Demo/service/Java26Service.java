package com.JohnBieniek.Java26Demo.service;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PEMDecoder;
import java.security.PEMEncoder;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;

import org.springframework.stereotype.Service;

import com.JohnBieniek.Java26Demo.client.Java26Client;
import com.JohnBieniek.Java26Demo.utility.Java26FeatureUtils;
import com.JohnBieniek.Java26Demo.utility.Java26FeatureUtils.FinalFieldHolder;
import com.JohnBieniek.Java26Demo.utility.Java26FeatureUtils.LazyValueDemo;

@Service
/**
 * Coordinates demonstrations of Java 26 language, API, concurrency, security,
 * runtime, and performance features.
 *
 * Transport concerns are delegated to Java26Client. Reusable computational
 * subroutines are delegated to Java26FeatureUtils.
 */
public class Java26Service {
    private final Java26Client java26Client;

    /**
     * Creates the service with the client responsible for outbound Java 26 HTTP calls.
     *
     * @param java26Client outbound HTTP client
     */
    public Java26Service(Java26Client java26Client) {
        this.java26Client = java26Client;
    }

    /**
     * Requests a URL with HTTP/3 enabled.
     *
     * @param url optional URL passed unchanged to the client layer
     * @return request and negotiated-protocol report
     */
    public String http3Demo(String url) {
        return java26Client.requestWithHttp3(url);
    }

    /**
     * Demonstrates primitive constants and primitive type patterns in a long switch.
     *
     * @param value value to classify
     * @return description produced by the matching primitive case
     */
    public String primitiveSwitchDemo(long value) {
        if (value == 0L) {
            return "zero";
        } else if (value == 1L) {
            return "one";
        } else if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
            return "fits exactly in int: " + (int) value;
        } else {
            return "long outside int range: " + value;
        }
    }

    /**
     * Demonstrates that a final LazyConstant initializes on first access and
     * invokes its computation only once.
     *
     * @return state and results from two accesses
     */
    public String lazyConstantDemo() {
        LazyValueDemo demo = new LazyValueDemo();
        boolean initializedBeforeAccess = demo.isInitialized();
        String first = demo.get();
        String second = demo.get();

        return "Before first access: initialized=" + initializedBeforeAccess + System.lineSeparator()
                + "Computing value once: computation count=" + demo.computationCount() + System.lineSeparator()
                + "First result: " + first + System.lineSeparator()
                + "Second result, same value: " + second.equals(first);
    }

    /**
     * Runs profile, order, and recommendation lookups as one structured task family.
     *
     * @return all lookup results, or one failure response when the scope fails
     */
    public Map<String, Object> structuredConcurrencyDemo() {
        try (var scope = StructuredTaskScope.<String>open()) {
            var profile = scope.fork(
                    () -> Java26FeatureUtils.fakeServiceCall("profile", "Ada Lovelace"));
            var orders = scope.fork(
                    () -> Java26FeatureUtils.fakeServiceCall("orders", "3 recent orders"));
            var recommendations = scope.fork(() -> Java26FeatureUtils.fakeServiceCall(
                    "recommendations", "Analytical Engine accessories"));

            scope.join();

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", "success");
            result.put("profile", profile.get());
            result.put("orders", orders.get());
            result.put("recommendations", recommendations.get());
            return result;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return Java26FeatureUtils.failure("Structured operation was interrupted.");
        } catch (RuntimeException exception) {
            return Java26FeatureUtils.failure("A related service call failed: " + exception.getMessage());
        }
    }

    /**
     * Generates an RSA key pair and round-trips the public key through PEM text.
     *
     * @return decoded-key details and equality result
     * @throws IllegalStateException when key generation, encoding, or decoding fails
     */
    public String pemDemo() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();

            String pem = PEMEncoder.of().encodeToString(keyPair.getPublic());
            PublicKey decoded = PEMDecoder.of().decode(pem, PublicKey.class);
            boolean matches = Arrays.equals(keyPair.getPublic().getEncoded(), decoded.getEncoded());

            return "PEM type: PUBLIC KEY" + System.lineSeparator()
                    + "Decoded algorithm: " + decoded.getAlgorithm() + System.lineSeparator()
                    + "Decoded key matches original: " + matches;
        } catch (Exception exception) {
            throw new IllegalStateException("PEM key round trip failed.", exception);
        }
    }

    /**
     * Attempts a deep-reflection mutation of a final instance field and reports the
     * Java 26 integrity behavior observed at runtime.
     *
     * @return mutation result or restriction details
     */
    public String finalFieldMutationWarningDemo() {
        FinalFieldHolder holder = new FinalFieldHolder("original");
        String before = holder.value();

        try {
            Field field = FinalFieldHolder.class.getDeclaredField("value");
            field.setAccessible(true);
            field.set(holder, "mutated through deep reflection");
            return "Before mutation: " + before + System.lineSeparator()
                    + "After mutation: " + holder.value() + System.lineSeparator()
                    + "Java 26 warns by default because deep reflection mutated a final field; "
                    + "a future release is expected to deny this by default.";
        } catch (ReflectiveOperationException exception) {
            return "Before mutation: " + before + System.lineSeparator()
                    + "Mutation was denied: " + exception.getClass().getSimpleName() + System.lineSeparator()
                    + "Java 26 final-field restrictions are active for this module.";
        }
    }

    /**
     * Compares scalar and Vector API implementations of integer-array addition.
     *
     * @return vector configuration, illustrative timings, sample values, and equality result
     */
    public Map<String, Object> vectorApiDemo() {
        int[] left = new int[1_024];
        int[] right = new int[1_024];
        Arrays.setAll(left, index -> index);
        Arrays.setAll(right, index -> index * 2);

        long scalarStarted = System.nanoTime();
        int[] scalarResult = Java26FeatureUtils.addWithScalarLoop(left, right);
        long scalarNanoseconds = System.nanoTime() - scalarStarted;

        long vectorStarted = System.nanoTime();
        int[] vectorResult = Java26FeatureUtils.addWithVectors(left, right);
        long vectorNanoseconds = System.nanoTime() - vectorStarted;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("arrayLength", left.length);
        result.put("preferredVectorBits", Java26FeatureUtils.preferredIntSpecies().vectorBitSize());
        result.put("lanesPerVector", Java26FeatureUtils.preferredIntSpecies().length());
        result.put("scalarNanoseconds", scalarNanoseconds);
        result.put("vectorNanoseconds", vectorNanoseconds);
        result.put("resultsMatch", Arrays.equals(scalarResult, vectorResult));
        result.put("sample", Arrays.copyOf(vectorResult, 8));
        result.put("timingNote", "Single-run timings are illustrative, not a benchmark.");
        return result;
    }

    /**
     * Inspects the active JVM for garbage collectors and explicit AOT-related arguments.
     *
     * @return runtime and AOT-related diagnostic information
     */
    public Map<String, Object> aotInfo() {
        var runtime = ManagementFactory.getRuntimeMXBean();
        List<String> aotArguments = runtime.getInputArguments().stream()
                .filter(Java26FeatureUtils::isAotRelatedArgument)
                .toList();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("javaVersion", Runtime.version().toString());
        result.put("vmName", runtime.getVmName());
        result.put("garbageCollectors", ManagementFactory.getGarbageCollectorMXBeans().stream()
                .map(bean -> bean.getName())
                .toList());
        result.put("aotRelatedJvmArguments", aotArguments);
        result.put("explicitAotArgumentsDetected", !aotArguments.isEmpty());
        return result;
    }

    /**
     * Attempts to load the Applet base class to demonstrate its Java 26 removal.
     *
     * @return runtime-specific class-loading result
     */
    public String appletRemovalDemo() {
        try {
            Class.forName("java.applet.Applet");
            return "java.applet.Applet is present; this runtime is not behaving like a standard Java 26 runtime.";
        } catch (ClassNotFoundException exception) {
            return "java.applet.Applet was not found: Java 26 removed the Applet API.";
        }
    }

}
