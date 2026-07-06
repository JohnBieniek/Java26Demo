package com.JohnBieniek.Java26Demo.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpServer;

@Service
public class Java11Service {

    /**
     * Demonstrates the standardized Java 11 HTTP client with a deterministic
     * loopback request to a temporary local HTTP server.
     */
    public String httpClientDemo() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
            server.createContext("/java11-demo", exchange -> {
                byte[] response = "Hello from the local Java 11 HTTP server."
                        .getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, response.length);
                exchange.getResponseBody().write(response);
                exchange.close();
            });
            server.start();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + server.getAddress().getPort() + "/java11-demo"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            return "status: " + response.statusCode() + " | body: " + response.body();
        } catch (IOException exception) {
            throw new IllegalStateException("Could not run the local HTTP client demo.", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The HTTP client demo was interrupted.", exception);
        } finally {
            if (server != null) {
                server.stop(0);
            }
        }
    }

    /**
     * Demonstrates Java 11 String.isBlank, lines, and strip.
     */
    public String stringMethodsDemo(String input) {
        String value = input == null ? "  first line  \n\n  second line  " : input;
        long nonBlankLines = value.lines()
                .map(String::strip)
                .filter(line -> !line.isBlank())
                .count();

        return "value used:"+value
                + " | is blank: " + value.isBlank()
                + " | stripped: [" + value.strip() + "]"
                + " | non-blank lines: " + nonBlankLines;
    }

    /**
     * Demonstrates Java 11 Optional.isEmpty.
     */
    public String optionalIsEmptyDemo(String value) {
        Optional<String> optional = Optional.ofNullable(value)
                .filter(text -> !text.isBlank());

        return optional.isEmpty()
                ? "Optional is empty."
                : "Optional contains: " + optional.get();
    }
}
