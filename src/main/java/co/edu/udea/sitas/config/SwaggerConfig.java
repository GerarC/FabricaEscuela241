package co.edu.udea.sitas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentación API de vuelo")
                        .version("1.0")
                        .description("Esta es la documentación del sistema de búsqueda de vuelos.")
                        .contact(new Contact()
                                .name("Ingenieria de Sistemas " + " - " + " Universidad de Antioquia")
                                .email("@udea.edu.co")
                                .url("https://flights.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("http://swagger.io/terms/"));
    }
}

