package com.pizzeria.pizzaapp.dto;

import java.util.HashMap;
import java.util.Map;

public class ErrorDto {
  public String message;
  public Map<String, String> fields = new HashMap<>();

  @SuppressWarnings("unused")
  public ErrorDto() {
  }

  public ErrorDto(String message) {
    this.message = message;
  }

  public ErrorDto(String message, Map<String, String> fields) {
    this.message = message;
    this.fields = fields;
  }
}
