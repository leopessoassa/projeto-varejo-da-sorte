package com.virtualize.varejodasorte.api.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {

    public static final String REQUEST_JWT_GRANTED_AUTHORITIES_ATTR_NAME = "JWT_GRANTED_AUTHORITIES_ATTR_NAME";
    public static final String REQUEST_JWT_LOGIN_ATTR_NAME = "JWT_LOGIN_ATTR_NAME";
    public static final String REQUEST_JWT_IP_ATTR_NAME = "JWT_IP_ATTR_NAME";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME_ATTR_NAME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LOGGER.debug("Received request: {} {} from {}", request.getMethod(), request.getRequestURI(),
                request.getRemoteAddr());
        request.setAttribute(LoggingInterceptor.START_TIME_ATTR_NAME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex)
            throws Exception {
        String executionTime = getExecutionTime(request);
        String statusCode = getStatusCode(response);

        MDC.put("source.ip", getRemoteAddr(request));
        MDC.put("dtp.categoria", "performance");
        MDC.put("dtp_app.url.path", request.getRequestURI());
        MDC.put("dtp_app.status_code", statusCode);
        MDC.put("tempo_resposta", executionTime);

        LOGGER.info("Sent response: {} {} with status {} time {} ms ", request.getMethod(),
                request.getRequestURI(), statusCode, executionTime);

        MDC.clear();
    }

    private String getStatusCode(HttpServletResponse response) {
        return String.valueOf(response.getStatus());
    }

    private String getExecutionTime(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR_NAME);
        if (startTime != null && startTime > 0L) {
            long endTime = System.currentTimeMillis();
            return String.valueOf(endTime - startTime);
        }
        return null;
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            LOGGER.debug("IP from proxy - X-FORWARDED-FOR : {}", ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
