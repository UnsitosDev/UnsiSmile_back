package edu.mx.unsis.unsiSmile.config;

//clase para la configuracion de swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "UNSISmile API",  // Title
                description = "API for the management of the dental clinic of the Universidad de la Sierra Sur of Oaxaca.",  // Provide a detailed description
                version = "0.1.0"  // Specify the API version
        )
)
public class OpenApiConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .components(new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        )
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
        }

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("public")
                        .pathsToMatch("/**")
                        .build();
        }
}