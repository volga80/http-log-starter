package ru.http_log_starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import ru.http_log_starter.aspect.HttpLoggingAspect;
import ru.http_log_starter.component.ControllerLogging;


@AutoConfiguration
@Slf4j
public class LoggingAutoConfiguration implements WebMvcConfigurer {
    @Bean
    public ControllerLogging controllerLogging() {
        return new ControllerLogging();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Запуск ControllerLogging");
        registry.addInterceptor(controllerLogging());
    }
}
