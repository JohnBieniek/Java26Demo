package com.JohnBieniek.Java26Demo.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.manager.Java26Manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@RequestMapping("/java26")
@Tag(
        name = "Java 26 Controller",
        description = "Runnable demonstrations of Java 26 language, library, concurrency, security, runtime, "
                + "and performance features. Preview and incubator examples run with the required JVM flags.")
public class Java26Controller {
    private final Java26Manager java26Manager;

    public Java26Controller(Java26Manager java26Manager) {
        this.java26Manager = java26Manager;
    }

    @GetMapping(value = "/http3Demo", produces = "text/plain")
    @Operation(
            summary = "Request a URL with Java 26 HTTP/3 support",
            description = "Asks the built-in HttpClient to use HTTP/3 and returns the requested URL, negotiated "
                    + "protocol, status code, and elapsed time. HTTP/3 is opportunistic, so the reported protocol "
                    + "may be HTTP/2 or HTTP/1.1. When url is omitted, Java26Client supplies its default HTTPS URL.")
    public String http3Demo(@RequestParam(required = false) String url) {
        return java26Manager.http3Demo(url);
    }

    @GetMapping(value = "/primitiveSwitchDemo", produces = "text/plain")
    @Operation(
            summary = "Use primitive patterns in a Java 26 preview switch",
            description = "Switches directly on a long. Zero and one use constant cases, values exactly "
                    + "representable as int match case int, and remaining values match case long.")
    public String primitiveSwitchDemo(@RequestParam long value) {
        return java26Manager.primitiveSwitchDemo(value);
    }

    @GetMapping(value = "/lazyConstantDemo", produces = "text/plain")
    @Operation(
            summary = "Initialize a Java 26 LazyConstant exactly once",
            description = "Creates a final LazyConstant, observes its uninitialized state, accesses it twice, "
                    + "and reports that its supplier ran once and both accesses returned the same value.")
    public String lazyConstantDemo() {
        return java26Manager.lazyConstantDemo();
    }

    @GetMapping("/structuredConcurrencyDemo")
    @Operation(
            summary = "Run three related service calls with structured concurrency",
            description = "Forks simulated profile, order, and recommendation lookups in one "
                    + "StructuredTaskScope. The scope joins them as a unit and returns either all results or a "
                    + "single clean failure response, preserving interruption and cancellation semantics.")
    public Map<String, Object> structuredConcurrencyDemo() {
        return java26Manager.structuredConcurrencyDemo();
    }

    @GetMapping(value = "/pemDemo", produces = "text/plain")
    @Operation(
            summary = "Round-trip an RSA public key through the Java 26 PEM API",
            description = "Generates a 2048-bit RSA key pair, encodes its public key with PEMEncoder, decodes it "
                    + "with PEMDecoder, and compares the original and decoded binary key encodings.")
    public String pemDemo() {
        return java26Manager.pemDemo();
    }

    @GetMapping(value = "/finalFieldMutationWarningDemo", produces = "text/plain")
    @Operation(
            summary = "Demonstrate Java 26 final-field mutation restrictions",
            description = "Attempts to mutate a final instance field with deep reflection. The response reports "
                    + "whether mutation occurred or was denied; Java 26 warns about permitted mutations by default "
                    + "in preparation for stronger integrity enforcement in future releases.")
    public String finalFieldMutationWarningDemo() {
        return java26Manager.finalFieldMutationWarningDemo();
    }

    @GetMapping("/vectorApiDemo")
    @Operation(
            summary = "Compare scalar and Vector API array addition",
            description = "Adds two 1,024-element integer arrays using a scalar loop and the incubating Vector "
                    + "API. Returns the preferred vector width, lane count, illustrative timings, sample output, "
                    + "and confirmation that both implementations produced identical results.")
    public Map<String, Object> vectorApiDemo() {
        return java26Manager.vectorApiDemo();
    }

    @GetMapping("/aotInfo")
    @Operation(
            summary = "Report GC and AOT-related runtime information",
            description = "Reads the running JVM rather than assuming its launch configuration. Returns the Java "
                    + "version, VM name, active garbage collectors, AOT/archive-related JVM arguments, and whether "
                    + "any explicit AOT arguments were detected.")
    public Map<String, Object> aotInfo() {
        return java26Manager.aotInfo();
    }

    @GetMapping(value = "/appletRemovalDemo", produces = "text/plain")
    @Operation(
            summary = "Show that Java 26 removed the Applet API",
            description = "Attempts to load java.applet.Applet by name. A standard Java 26 runtime throws "
                    + "ClassNotFoundException because the obsolete Applet API has been removed.")
    public String appletRemovalDemo() {
        return java26Manager.appletRemovalDemo();
    }
}
