package com.trading.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Configuration for API versioning.
 * This configures versioning for the API endpoints.
 */
@Configuration
public class ApiVersioningConfig implements WebMvcConfigurer {

    /**
     * Configure API versioning using header-based versioning.
     * This allows clients to specify API version in the Accept header.
     *
     * @return the custom request mapping handler mapping
     */
    @Bean
    @Primary
    public RequestMappingHandlerMapping apiVersionRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }

    /**
     * Custom request mapping handler mapping for API versioning.
     */
    private static class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        // Custom implementation can be added here if needed
    }
}
