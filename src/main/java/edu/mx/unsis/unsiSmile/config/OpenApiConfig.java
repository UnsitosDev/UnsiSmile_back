package edu.mx.unsis.unsiSmile.config;

//clase para la configuracion de swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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


}