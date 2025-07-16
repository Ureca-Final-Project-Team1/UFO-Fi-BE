package com.example.ufo_fi.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "local server url"),
                @Server(url = "https://api.ufo-fi.store", description = "deploy server url")
        }
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(createApiInfo());
    }

    private io.swagger.v3.oas.models.info.Info createApiInfo() {
        return new io.swagger.v3.oas.models.info.Info()
                .title("UFO-FI API")
                .description("UFO-FI API 명세서")
                .version("1.0.0");
    }
}
