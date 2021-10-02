package com.pizzeria.pizzaapp.db;

public class PostgresqlQueryBuilder extends QueryBuilder {

  public PostgresqlQueryBuilder(String sql) {
    super(sql);
  }

  public PostgresqlQueryBuilder(Class<?> modelClass) {
    super(modelClass);
  }

  public PostgresqlQueryBuilder(String select, Class<?> modelClass) {
    super(select, modelClass);
  }

  @Override
  public String toSql() {
    if (limit != null && offset != null) offsetString = " limit " + limit + " offset " + offset;
    return super.toSql();
  }
}