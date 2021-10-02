package com.pizzeria.pizzaapp.db;

import com.pizzeria.pizzaapp.domain.user.User.Role;
import org.postgresql.jdbc.PgArray;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleListConverter implements AttributeConverter<Set<Role>, Object> {
  @Override
  public String[] convertToDatabaseColumn(Set<Role> value) {
    return value.stream().map(Enum::toString).toArray(String[]::new);
  }

  @Override
  public Set<Role> convertToEntityAttribute(Object dbValue) {
    try {
      var pgArray = (PgArray) dbValue;
      var array = (String[]) pgArray.getArray();
      return Arrays.stream(array)
        .map(Role::valueOf)
        .collect(Collectors.toSet());
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
