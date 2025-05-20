package com.trading.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Configuration for OpenAPI documentation.
 * This enhances the API documentation with additional information.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure OpenAPI documentation.
     *
     * @return the OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trading API")
                        .description("RESTful API for trading system with comprehensive CRUD operations")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Trading API Team")
                                .email("support@tradingapi.com")
                                .url("https://tradingapi.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(Arrays.asList(
                        new Server().url("/").description("Default Server URL"),
                        new Server().url("http://localhost:8080").description("Development Server")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
