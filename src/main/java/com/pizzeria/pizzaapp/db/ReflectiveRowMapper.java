package com.pizzeria.pizzaapp.db;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ReflectiveRowMapper<T> implements RowMapper<T> {

  private Class<T> type;
  private List<Field> simpleMappableFields;
  private List<Field> converterMappableFields;
  private Map<Class<? extends AttributeConverter>, AttributeConverter> converters;

  LobHandler lobHandler = new DefaultLobHandler();

  final ReflectiveRowMapper<T> init(Class<T> type,
                                    List<Field> simpleMappableFields,
                                    List<Field> converterMappableFields,
                                    Map<Class<? extends AttributeConverter>, AttributeConverter> converters) {
    this.type = type;
    this.simpleMappableFields = simpleMappableFields;
    this.converterMappableFields = converterMappableFields;
    this.converters = converters;

    return this;
  }

  @Override
  public T mapRow(ResultSet rs, int rowNum) throws SQLException {
    try {
      T result = createInstance();

      for (Field field : simpleMappableFields) {
        mapSimpleField(rs, result, field);
      }

      for (Field field : converterMappableFields) {
        mapConvertableField(rs, result, field);
      }

      return result;
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance", e);
    }
  }

  private void mapConvertableField(ResultSet rs, T result, Field field) throws SQLException, IllegalAccessException {
    Object value = rs.getObject(field.getAnnotation(Column.class).name());
    //noinspection unchecked
    Object convertedValue = converters.get(field.getAnnotation(Convert.class).converter())
      .convertToEntityAttribute(value);
    field.set(result, convertedValue);
  }

  private void mapSimpleField(ResultSet rs, T result, Field field) throws SQLException, IllegalAccessException {
    String columnName = field.getAnnotation(Column.class).name();
    Object object = rs.getObject(columnName);
    mapSimpleField(rs, result, field, columnName, object);
  }

  protected void mapSimpleField(ResultSet rs, T result, Field field, String columnName, Object object)
      throws IllegalAccessException, SQLException {
    if (object instanceof Date) {
      Date date = (Date) object;
      field.set(result, new java.util.Date(date.getTime()));
    } else if (object instanceof Timestamp) {
      Timestamp ts = (Timestamp) object;
      field.set(result, new java.util.Date(ts.getTime()));
    } else if (field.getType() != null && field.getType().isEnum() && object != null) {
      //noinspection unchecked
      field.set(result, Enum.valueOf((Class<? extends Enum>)field.getType(), object.toString()));
    } else if (object instanceof Clob) {
      field.set(result, lobHandler.getClobAsString(rs, columnName));
    } else {
      field.set(result, object);
    }
  }

  private T createInstance() throws IllegalAccessException, InstantiationException {
    return type.newInstance();
  }
}