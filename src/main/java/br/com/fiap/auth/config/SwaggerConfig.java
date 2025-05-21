package br.com.fiap.auth.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Autenticação - DWE")
                .version("1.0")
                .description("Documentação da API de autenticação com Spring Boot e JWT"));
    }
}