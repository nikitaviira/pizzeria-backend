package com.pizzeria.pizzaapp.config.exceptions;

import static com.pizzeria.pizzaapp.util.Translation.toLocale;

public class ServiceException extends RuntimeException {
  public String errorLocaleCode;

  public ServiceException(String errorLocaleCode) {
    this.errorLocaleCode = errorLocaleCode;
  }

  @Override
  public String getLocalizedMessage() {
    return toLocale(this.errorLocaleCode);
  }
}
