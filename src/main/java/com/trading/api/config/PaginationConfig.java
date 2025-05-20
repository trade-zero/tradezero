package com.trading.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration for pagination and sorting.
 * This configures default pagination settings for the API.
 */
@Configuration
public class PaginationConfig implements WebMvcConfigurer {

    /**
     * Configure pagination defaults.
     *
     * @param argumentResolvers list of argument resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(PageRequest.of(0, 20)); // Default page size of 20
        argumentResolvers.add(resolver);
    }
}
