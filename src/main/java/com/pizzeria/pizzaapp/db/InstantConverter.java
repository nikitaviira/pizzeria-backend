package com.pizzeria.pizzaapp.db;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class InstantConverter implements AttributeConverter<Instant, Timestamp> {
  @Override
  public Timestamp convertToDatabaseColumn(Instant value) {
    return value == null ? null : new Timestamp(value.toEpochMilli());
  }

  @Override
  public Instant convertToEntityAttribute(Timestamp dbData) {
    return dbData == null ? null : dbData.toInstant();
  }
}
