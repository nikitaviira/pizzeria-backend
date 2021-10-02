package com.pizzeria.pizzaapp.dto;

public class JwtTokenDto {
  public Long userId;
  public String email;
  public String hash;
  public long expiresAt;
}
