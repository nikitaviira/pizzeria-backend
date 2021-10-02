package com.pizzeria.pizzaapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

import static com.pizzeria.pizzaapp.util.Translation.RUSSIAN;
import static java.util.Locale.ENGLISH;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

@Configuration
public class Internationalization {
  public static final List<Locale> supportedLocales = List.of(ENGLISH, RUSSIAN);

  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
    resolver.setDefaultLocale(ENGLISH);
    resolver.setSupportedLocales(supportedLocales);
    return resolver;
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("lang/localize");
    messageSource.setDefaultEncoding(UTF_8);
    return messageSource;
  }

  @Bean
  LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }
}
