package ru.http_log_starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import ru.http_log_starter.component.RestTemplateLogging;

import java.util.ArrayList;
import java.util.List;

@AutoConfiguration(after = LoggingAutoConfiguration.class)
@Slf4j
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        log.info("Конфигурация RestTemplate");
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()
        ));
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RestTemplateLogging());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
