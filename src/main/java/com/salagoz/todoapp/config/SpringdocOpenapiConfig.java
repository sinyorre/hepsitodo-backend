package com.salagoz.todoapp.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringdocOpenapiConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Hepsitodo API")
                        .description("Sample todo application")
                        .version("v0.0.1")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("http://springdoc.org")
                        )
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("SpringShop Wiki Documentation")
                                .url("https://springshop.wiki.github.org/docs")
                );
    }
}
