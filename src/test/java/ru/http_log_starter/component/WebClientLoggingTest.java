package ru.http_log_starter.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebClientLoggingTest {
    @InjectMocks
    private WebClientLogging webClientLogging;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void logRequestShouldLogRequestDetails() {
        WebClient webClient = WebClient.builder()
                .filter(webClientLogging.logRequest())
                .build();

        webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Test
    public void logResponseShouldLogResponseDetails() {
        WebClient webClient = WebClient.builder()
                .filter(webClientLogging.logResponse())
                .build();

        webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}