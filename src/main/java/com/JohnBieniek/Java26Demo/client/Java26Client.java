package com.JohnBieniek.Java26Demo.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
/**
 * Owns the reusable Java HTTP client used by the Java 26 HTTP/3 demonstration.
 *
 * The component opts into HTTP/3, resolves missing input to a default HTTPS target,
 * validates supported URI schemes, executes requests, formats results, and closes its
 * underlying client when Spring destroys the bean.
 */
public class Java26Client implements AutoCloseable {
    /** Default target used when no URL is supplied by the caller. */
    public static final String DEFAULT_URL = "https://www.google.com/";

    private final HttpClient httpClient;

    /**
     * Creates a reusable client that prefers HTTP/3, follows normal redirects, and
     * uses a bounded connection timeout.
     */
    public Java26Client() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_3)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    /**
     * Executes a GET request with HTTP/3 enabled and reports the protocol actually negotiated.
     *
     * @param url target URL, or null/blank to use DEFAULT_URL
     * @return requested URL, negotiated protocol, status code, and elapsed time
     * @throws IllegalArgumentException when the URL is invalid or does not use HTTP(S)
     * @throws IllegalStateException when the request fails or is interrupted
     */
    public String requestWithHttp3(String url) {
        URI uri = toHttpUri(url);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        long startedAt = System.nanoTime();

        try {
            HttpResponse<Void> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.discarding());
            long elapsedMilliseconds = Duration.ofNanos(System.nanoTime() - startedAt).toMillis();

            return "Requested URL: " + uri + System.lineSeparator()
                    + "HTTP protocol actually used: " + displayName(response.version()) + System.lineSeparator()
                    + "Status code: " + response.statusCode() + System.lineSeparator()
                    + "Elapsed time: " + elapsedMilliseconds + " ms";
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("HTTP request was interrupted.", exception);
        } catch (IOException exception) {
            throw new IllegalStateException("HTTP request failed: " + exception.getMessage(), exception);
        }
    }

    /**
     * Resolves optional input to a validated HTTP or HTTPS URI.
     *
     * @param url supplied URL, which may be absent or blank
     * @return validated URI, using the client default when necessary
     */
    URI toHttpUri(String url) {
        URI uri = URI.create(url == null || url.isBlank() ? DEFAULT_URL : url);
        if (!"http".equalsIgnoreCase(uri.getScheme()) && !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("URL must use the http or https scheme.");
        }
        return uri;
    }

    /**
     * Converts the protocol enum to its familiar wire-protocol spelling.
     *
     * @param version negotiated HTTP version
     * @return display name such as HTTP/3
     */
    private String displayName(HttpClient.Version version) {
        return switch (version) {
            case HTTP_1_1 -> "HTTP/1.1";
            case HTTP_2 -> "HTTP/2";
            case HTTP_3 -> "HTTP/3";
        };
    }

    @Override
    /** Closes the underlying HTTP client and releases its resources. */
    public void close() {
        httpClient.close();
    }
}
