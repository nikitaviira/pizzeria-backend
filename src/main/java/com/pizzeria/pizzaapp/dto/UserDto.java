package com.pizzeria.pizzaapp.dto;

import com.pizzeria.pizzaapp.domain.user.User;
import com.pizzeria.pizzaapp.domain.user.User.Role;

import java.util.Set;

public class UserDto {
  public String username;
  public String fullName;
  public Set<Role> roles;

  public UserDto() {
  }

  public UserDto(User user) {
    this.username = user.email;
    this.fullName = user.getFullName();
    this.roles = user.roles;
  }
}
