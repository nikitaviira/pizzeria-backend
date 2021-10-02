package com.pizzeria.pizzaapp.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translation {
  private static ResourceBundleMessageSource messageSource;

  public static Locale RUSSIAN = new Locale("ru");

  public Translation(ResourceBundleMessageSource messageSource) {
    Translation.messageSource = messageSource;
  }

  public static String toLocale(String msgCode) {
    Locale locale = getCurrentLocale();
    return messageSource.getMessage(msgCode, null, locale);
  }

  public static Locale getCurrentLocale() {
    return LocaleContextHolder.getLocale();
  }
}
