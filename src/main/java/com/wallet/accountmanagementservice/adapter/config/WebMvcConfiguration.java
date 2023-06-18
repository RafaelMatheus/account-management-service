package com.wallet.accountmanagementservice.adapter.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new MDCInterceptor());
    }

    public static class MDCInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
            var requestTraceId = request.getHeader("traceId");

            if (Objects.isNull(requestTraceId)) {
                requestTraceId = UUID.randomUUID().toString();
            }
            MDC.put("traceId", requestTraceId);
            return true;
        }

        @Override
        public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                                    @Nullable final Exception ex) throws Exception {
            MDC.clear();
        }

    }
}