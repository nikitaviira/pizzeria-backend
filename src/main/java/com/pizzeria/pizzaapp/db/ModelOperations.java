package com.pizzeria.pizzaapp.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.concat;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.util.StringUtils.isEmpty;

@Component
@Scope(SCOPE_PROTOTYPE)
public class ModelOperations<T> {

  @Autowired
  ApplicationContext applicationContext;

  private Class<T> type;
  private Map<Class<? extends AttributeConverter>, AttributeConverter> converters;
  private List<Field> simpleMappableFields;
  private List<Field> converterMappableFields;
  private Field idField;
  private String tableName;
  private String notGeneratedIdColumnName;

  private RowMapper<T> rowMapper;

  public ModelOperations(Class<T> type) {
    this.type = type;
  }

  public void init() {
    List<Field> mappableFields = getMappableFields(Column.class);
    simpleMappableFields = mappableFields.stream()
      .filter(f -> !f.isAnnotationPresent(Convert.class)).collect(toList());
    converterMappableFields = mappableFields.stream()
      .filter(f -> f.isAnnotationPresent(Convert.class)).collect(toList());
    makeMappableFieldsAccessible();
    converters = converterMappableFields.stream()
      .map(f -> f.getAnnotation(Convert.class).converter()).distinct()
      .collect(toMap(c -> c, this::attributeConverter));
    idField = mappableFields.stream().filter(f -> f.isAnnotationPresent(Id.class)).findFirst().orElse(null);

    if (type.isAnnotationPresent(Table.class)) {
      tableName = type.getAnnotation(Table.class).name();
    }

    rowMapper = createReflectiveRowMapperInstance()
      .init(type, simpleMappableFields, converterMappableFields, converters);
    notGeneratedIdColumnName = getColumnIdName(mappableFields);
  }

  private String getColumnIdName(List<Field> mappableFields) {
    Field field = mappableFields.stream()
      .filter(f -> f.isAnnotationPresent(Id.class))
      .filter(f -> f.isAnnotationPresent(Column.class))
      .findFirst().orElse(null);

    if (field == null || field.getAnnotation(GeneratedValue.class) != null) {
      return null;
    }

    return field.getAnnotation(Column.class).name();
  }

  protected ReflectiveRowMapper<T> createReflectiveRowMapperInstance() {
    return new ReflectiveRowMapper<T>();
  }

  private AttributeConverter attributeConverter(Class<? extends AttributeConverter> clazz) {
    return applicationContext.getBean(clazz);
  }

  private void makeMappableFieldsAccessible() {
    concat(simpleMappableFields.stream(), converterMappableFields.stream())
      .forEach(f -> f.setAccessible(true));
  }

  public RowMapper<T> getRowMapper() {
    return rowMapper;
  }

  public String getInsertSql() {
    if (isEmpty(tableName)) {
      throw new UnsupportedOperationException("Insert operation is not allowed - model has not table name specified");
    }
    String namedString = getNamedStringForInsert();
    return "insert into " + tableName + "(" + getColumnNamesForInsert() + ") values(" + namedString + ")";
  }

  public String getUpdateSql() {
    if (isEmpty(tableName)) {
      throw new UnsupportedOperationException("Update operation is not allowed - model has not table name specified");
    }
    if (idField == null) {
      throw new UnsupportedOperationException("Update operation is not allowed - model has no @Id field");
    }
    return "update " + tableName + " set " + getPairsForUpdate() + " where "
      + idField.getAnnotation(Column.class).name() + "=:" + idField.getName();
  }

  public String getDeleteSql() {
    if (isEmpty(tableName)) {
      throw new UnsupportedOperationException("Update operation is not allowed - model has not table name specified");
    }
    if (idField == null) {
      throw new UnsupportedOperationException("Update operation is not allowed - model has no @Id field");
    }
    return "delete from " + tableName + " where "
      + idField.getAnnotation(Column.class).name() + "=:" + idField.getName();
  }

  private String getNamedStringForInsert() {
    return Stream.concat(
      simpleMappableFields.stream().filter(f -> !f.isAnnotationPresent(GeneratedValue.class)),
      converterMappableFields.stream().filter(f -> !f.isAnnotationPresent(GeneratedValue.class)))
      .map(f -> ":" + f.getName()).collect(joining(","));
  }

  private String getPairsForUpdate() {
    return Stream.concat(simpleMappableFields.stream(), converterMappableFields.stream())
      .filter(f -> !f.equals(idField))
      .map(f -> f.getAnnotation(Column.class).name() + "=:" + f.getName())
      .collect(joining(","));
  }

  private String getColumnNamesForInsert() {
    return concat(
      simpleMappableFields.stream().filter(f -> !f.isAnnotationPresent(GeneratedValue.class)),
      converterMappableFields.stream().filter(f -> !f.isAnnotationPresent(GeneratedValue.class)))
      .map(f -> f.getAnnotation(Column.class).name()).collect(joining(","));
  }

  public Map<String, Object> getParametersForInsertOrUpdate(T model) {
    Map<String, Object> result = new HashMap<>();
    simpleMappableFields.forEach(f ->
      result.put(f.getName(), normalizeValueForInsertOrUpdate(getFieldValue(f, model)))
    );
    converterMappableFields.forEach(f -> {
      //noinspection unchecked
      result.put(
        f.getName(),
        converters.get(f.getAnnotation(Convert.class).converter()).convertToDatabaseColumn(getFieldValue(f, model))
      );
    });
    return result;
  }

  public Map<String, Object>[] getParametersForBatchInsertOrUpdate(List<T> models) {
    //noinspection unchecked
    return models.stream().map(this::getParametersForInsertOrUpdate).collect(toList()).toArray(new Map[models.size()]);
  }

  private Object normalizeValueForInsertOrUpdate(Object value) {
    if (value instanceof Enum) {
      Enum anEnum = (Enum) value;
      return anEnum.name();
    }
    return value;
  }

  private Object getFieldValue(Field field, Object obj) {
    try {
      return field.get(obj);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Field> getMappableFields(Class<? extends Annotation> annotation) {
    Class<?> clazz = type;
    List<Field> result = new ArrayList<>();
    while (clazz.getSuperclass() != null) {
      result.addAll(asList(clazz.getDeclaredFields()).stream()
        .filter(f -> f.isAnnotationPresent(annotation)).collect(toList()));
      clazz = clazz.getSuperclass();
    }
    return result;
  }

  public String getNotGeneratedIdColumnName() {
    return notGeneratedIdColumnName;
  }
}
