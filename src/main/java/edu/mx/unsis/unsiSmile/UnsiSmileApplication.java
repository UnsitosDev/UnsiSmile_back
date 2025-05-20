package edu.mx.unsis.unsiSmile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "edu.mx.unsis.unsiSmile.service",
    "edu.mx.unsis.unsiSmile.controller",
    // ...otros paquetes necesarios
})
public class UnsiSmileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnsiSmileApplication.class, args);
	}

}
