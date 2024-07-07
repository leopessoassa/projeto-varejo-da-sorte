package com.virtualize.varejo_da_sorte.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${info.app.version:unknown}")
    private String version;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("VAREJO DA SORTE - API")
                        .description("API Rest para o projeto do Varejo da Sorte")
                        .version(version)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
