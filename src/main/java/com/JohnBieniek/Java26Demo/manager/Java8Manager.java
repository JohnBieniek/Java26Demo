package com.JohnBieniek.Java26Demo.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.JohnBieniek.Java26Demo.model.animal.Beetle;

/** Coordinates demonstrations of Java 8 language and library features. */
@Service
public class Java8Manager {
    private static final Logger logger = LoggerFactory.getLogger(Java8Manager.class);

    /**
     * Demonstrates a lambda that duplicates an input string.
     *
     * @param input string to duplicate
     * @return duplicated input separated by an ampersand
     */
    public String demoLambdaStringDoubling(String input) {
        Function<String, String> runnable = currentInput -> {
            String updatedResult = currentInput + "&" + currentInput;
            logger.info("Running the lambda with input: {} and result: {}", currentInput, updatedResult);
            return updatedResult;
        };
        return runnable.apply(input);
    }

    /**
     * Demonstrates Iterable.forEach with a lambda.
     *
     * @return joined names processed by the lambda
     */
    public String demoForEachLambda() {
        StringBuilder updatedResult = new StringBuilder();
        ArrayList<String> names = new ArrayList<>(List.of(
                "Alice", "Bob", "Charlie", "David", "Eve",
                "Frank", "Grace", "Heidi", "Ivan", "Judy"));

        names.forEach(name -> {
            if (updatedResult.length() > 0) {
                updatedResult.append("&");
            }
            updatedResult.append(name);
            logger.info("Running the lambda with input: {} and result: {}", name, updatedResult);
        });

        return "Names processed with forEach lambda: " + updatedResult;
    }

    /**
     * Demonstrates a default interface method on an animal implementation.
     *
     * @return flight descriptions from the default and implemented methods
     */
    public String demoDefaultInterfaceMethod() {
        Beetle beetle = new Beetle();
        String result = beetle.describeFlight() + " " + beetle.fly();
        logger.info("Default interface demo: {}", result);
        return result;
    }

    /**
     * Demonstrates a method reference that converts stream elements to uppercase.
     *
     * @return uppercase names separated by commas
     */
    public String demoMethodReference() {
        List<String> names = List.of("Alice", "Bob", "Charlie");
        String result = names.stream()
                .map(String::toUpperCase)
                .reduce((left, right) -> left + ", " + right)
                .orElse("No names");

        logger.info("Method reference demo: {}", result);
        return result;
    }

    /**
     * Demonstrates stream filtering, mapping, and reduction.
     *
     * @return uppercase names that begin with vowels
     */
    public String demoStreamFilterAndReduce() {
        List<String> names = List.of(
                "Alice", "Bob", "Charlie", "David", "Eve",
                "Frank", "Grace", "Heidi", "Ivan", "Judy");
        String result = names.stream()
                .filter(name -> name.startsWith("A")
                        || name.startsWith("E")
                        || name.startsWith("I")
                        || name.startsWith("O")
                        || name.startsWith("U"))
                .map(String::toUpperCase)
                .reduce((left, right) -> left + " & " + right)
                .orElse("No names found");
        return "Names starting with a vowel from A-I, filtered with a stream:" + result;
    }

    /**
     * Demonstrates Optional fallback behavior.
     *
     * @param input optional input
     * @return supplied input or the default, followed by completion text
     */
    public String testOptional(Optional<String> input) {
        return input.orElse("No input") + " complete";
    }
}
