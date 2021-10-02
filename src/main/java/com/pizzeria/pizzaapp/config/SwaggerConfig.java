package com.pizzeria.pizzaapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    Pattern publicApiPattern = Pattern.compile("/api/.*");

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(path -> publicApiPattern.matcher(path).matches())
        .build()
        .directModelSubstitute(BigDecimal.class, String.class);
  }
}
