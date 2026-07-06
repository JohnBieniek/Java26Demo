package com.JohnBieniek.Java26Demo.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.JohnBieniek.Java26Demo.java9.manager.Java9Manager;

@Service
public class Java9ModuleService {
    private static final String MODULE_MAIN =
            "com.JohnBieniek.Java26Demo.java.nine/"
                    + "com.JohnBieniek.Java26Demo.java9.manager.Java9ModuleDemoRunner";

    /**
     * Launches the dedicated Java 9 demo as a real named JPMS module and returns
     * the module information reported from inside that modular JVM.
     */
    public String moduleDemo() {
        try {
            Path modulePath = Path.of(
                    Java9Manager.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            );
            String executableName = System.getProperty("os.name").startsWith("Windows")
                    ? "java.exe"
                    : "java";
            Path javaExecutable = Path.of(System.getProperty("java.home"), "bin", executableName);

            Process process = new ProcessBuilder(
                    javaExecutable.toString(),
                    "--module-path",
                    modulePath.toString(),
                    "--module",
                    MODULE_MAIN
            ).redirectErrorStream(true).start();

            if (!process.waitFor(10, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new IllegalStateException("The Java 9 module demo timed out.");
            }

            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).strip();
            if (process.exitValue() != 0) {
                throw new IllegalStateException("The Java 9 module demo failed: " + output);
            }
            return output;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not launch the Java 9 module demo.", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The Java 9 module demo was interrupted.", exception);
        } catch (Exception exception) {
            throw new IllegalStateException("Could not locate the Java 9 module.", exception);
        }
    }

    /**
     * Delegates to the manager compiled inside the Java 9 module to demonstrate
     * List.of, Set.of, and Map.of.
     */
    public String immutableCollectionsDemo() {
        return new Java9Manager().immutableCollectionsDemo();
    }
}
