package com.pizzeria.pizzaapp.dto;

public class LoginResponseDto {
  public String jwtToken;
  public UserDto user;

  @SuppressWarnings("unused")
  public LoginResponseDto() {
  }

  public LoginResponseDto(String jwtToken, UserDto user) {
    this.jwtToken = jwtToken;
    this.user = user;
  }
}
