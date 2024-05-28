package ru.http_log_starter.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import ru.http_log_starter.component.WebClientLogging;

@AutoConfiguration(after = RestTemplateConfig.class)
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        log.info("Конфигурация WebClient");
        WebClientLogging webClientLogging = new WebClientLogging();
        return WebClient.builder()
                .filter(webClientLogging.logRequest())
                .filter(webClientLogging.logResponse())
                .build();
    }
}