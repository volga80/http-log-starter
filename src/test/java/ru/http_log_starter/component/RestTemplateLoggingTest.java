package ru.http_log_starter.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestTemplateLoggingTest {
    @InjectMocks
    private RestTemplateLogging restTemplateLogging;
    @Mock
    private HttpRequest request;
    @Mock
    private ClientHttpRequestExecution execution;

    private byte[] body = "Test body".getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void interceptShouldLogRequestAndResponse() throws IOException {
        MockClientHttpResponse mockResponse = new MockClientHttpResponse(new ByteArrayInputStream("Test response".getBytes()), 200);

        when(request.getURI()).thenReturn(new MockClientHttpRequest().getURI());
        when(request.getMethod()).thenReturn(new MockClientHttpRequest().getMethod().GET);
        when(request.getHeaders()).thenReturn(new MockClientHttpRequest().getHeaders());
        when(execution.execute(request, body)).thenReturn(mockResponse);

        restTemplateLogging.intercept(request, body, execution);

        verify(execution).execute(request, body);
    }
}