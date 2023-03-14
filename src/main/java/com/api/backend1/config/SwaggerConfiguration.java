package com.api.backend1.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)

                // Configura quais controllers devem ser documentados
                .select()
                .apis(RequestHandlerSelectors.any())

                // Configura quais endpoints devem ser documentados
                .paths(PathSelectors.any())
                .build()

                // Define um mapeamento de caminho para o Swagger UI
                .pathMapping("/")

                .apiInfo(new ApiInfoBuilder()
                        .title("Trevo Project")
                        .description("Trevo agro api")
                        .build());
    }

}
