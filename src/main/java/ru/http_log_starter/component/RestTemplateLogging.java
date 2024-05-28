package ru.http_log_starter.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RestTemplateLogging implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        log.info("Вызов перехватчика RestTemplateLogging");
        long startTime = System.currentTimeMillis();
        logRequestDetails(request, body);

        ClientHttpResponse response = execution.execute(request, body);
        long endTime = System.currentTimeMillis();
        logResponseDetails(response, endTime - startTime);

        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) {
        log.info("URI запроса {}, Метод запроса: {}, Заголовки запроса: {}, Тело запроса: {}",
                request.getURI(), request.getMethod(), request.getHeaders(),
                new String(body, StandardCharsets.UTF_8));
    }

    private void logResponseDetails(ClientHttpResponse response, long duration) throws IOException {
        log.info("Статус ответа: {}, Заголовки ответа: {}, Время выполнения {}ms, Тело ответа: {}",
                response.getStatusCode(), response.getHeaders(), duration,
                new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8));
    }
}
