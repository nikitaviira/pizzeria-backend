package com.pizzeria.pizzaapp.controllers;

import com.pizzeria.pizzaapp.domain.user.UserService;
import com.pizzeria.pizzaapp.dto.LoginRequestDto;
import com.pizzeria.pizzaapp.dto.LoginResponseDto;
import com.pizzeria.pizzaapp.dto.RegisterRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user/login")
  public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
    return userService.login(loginRequestDto);
  }

  @PostMapping("/user/register")
  public LoginResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
    return userService.register(registerRequestDto);
  }
}
