package com.JohnBieniek.Java26Demo.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

class Java26ClientTests {
    @Test
    void reportsTheProtocolActuallyUsed() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/demo", exchange -> {
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
        });
        server.start();

        try (Java26Client client = new Java26Client()) {
            String url = "http://localhost:" + server.getAddress().getPort() + "/demo";

            assertThat(client.requestWithHttp3(url))
                    .contains("Requested URL: " + url)
                    .contains("HTTP protocol actually used: HTTP/1.1")
                    .contains("Status code: 204")
                    .containsPattern("Elapsed time: \\d+ ms");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void usesDefaultUrlWhenUrlIsMissing() {
        try (Java26Client client = new Java26Client()) {
            assertThat(client.toHttpUri(null).toString()).isEqualTo(Java26Client.DEFAULT_URL);
            assertThat(client.toHttpUri("  ").toString()).isEqualTo(Java26Client.DEFAULT_URL);
        }
    }

    @Test
    void rejectsNonHttpUrls() {
        try (Java26Client client = new Java26Client()) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> client.requestWithHttp3("file:///tmp/demo.txt"))
                    .withMessage("URL must use the http or https scheme.");
        }
    }
}
