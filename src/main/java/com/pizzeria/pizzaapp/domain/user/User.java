package com.pizzeria.pizzaapp.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pizzeria.pizzaapp.db.IdAware;
import com.pizzeria.pizzaapp.db.InstantConverter;
import com.pizzeria.pizzaapp.db.RoleListConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Table(name = "users")
public class User implements IdAware {
  @Id
  @GeneratedValue
  @Column(name = "id")
  public Long id;

  @Column(name = "email")
  public String email;

  @Column(name = "first_name")
  public String firstName;

  @Column(name = "last_name")
  public String lastName;

  @Column(name = "password_hash")
  public String passwordHash;

  @Column(name = "created_at")
  @Convert(converter = InstantConverter.class)
  public Instant createdAt;

  @Column(name = "roles")
  @Convert(converter = RoleListConverter.class)
  public Set<Role> roles;

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }

  @JsonIgnore
  public String getFullName() {
    return capitalize(this.firstName) + " " + capitalize(this.lastName);
  }

  public enum Role {
    ADMIN, USER, DELIVERY
  }
}
