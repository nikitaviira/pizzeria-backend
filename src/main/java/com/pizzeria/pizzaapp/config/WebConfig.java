package com.pizzeria.pizzaapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${cors.allowed-origins}")
  private String[] allowedOrigins;
  private final HeaderCallerContextArgumentResolver headerCallerContextArgumentResolver;

  public WebConfig(HeaderCallerContextArgumentResolver headerCallerContextArgumentResolver) {
    this.headerCallerContextArgumentResolver = headerCallerContextArgumentResolver;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/pizzeria/**")
      .allowedOrigins(allowedOrigins)
      .allowedMethods("GET", "POST");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(headerCallerContextArgumentResolver);
  }
}
