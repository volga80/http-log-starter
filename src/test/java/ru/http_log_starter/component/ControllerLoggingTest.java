package ru.http_log_starter.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


@ExtendWith(MockitoExtension.class)
public class ControllerLoggingTest {

    @InjectMocks
    private ControllerLogging controllerLogging;
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    @Mock
    private Object handler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
    }

    @Test
    public void preHandleShouldLogRequestDetails() throws Exception {
        mockRequest.setMethod("GET");
        mockRequest.setRequestURI("/test-uri");
        mockRequest.addHeader("Header1", "Value1");

        boolean preHandleResult = controllerLogging.preHandle(mockRequest, mockResponse, new Object());

        assertThat(preHandleResult).isTrue();
        assertThat(mockRequest.getAttribute("startTime")).isNotNull();
    }

    @Test
    public void postHandleShouldLogResponseDetails() throws Exception {
        long startTime = System.currentTimeMillis();
        when(request.getAttribute("startTime")).thenReturn(startTime);

        mockResponse.setStatus(200);
        mockResponse.addHeader("Header1", "Value1");

        controllerLogging.postHandle(request, mockResponse, handler, null);

        verify(request).getAttribute("startTime");
    }
}
