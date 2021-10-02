package com.pizzeria.pizzaapp.db;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class QueryBuilder {

  private String sql;
  private List<Clause> clauses = new ArrayList<>();
  private String orderBy;
  private boolean forUpdate;
  private boolean noWait;
  private boolean count;
  protected Long limit;
  protected Long offset;
  protected String offsetString;

  public QueryBuilder() {

  }

  public QueryBuilder(String sql) {
    this.sql = sql;
  }

  public QueryBuilder(Class<?> modelClass) {
    this("select " + columns(modelClass) + " from " + modelClass.getAnnotation(Table.class).name());
  }

  public QueryBuilder(String select, Class<?> modelClass) {
    this("select " + select + " from " + modelClass.getAnnotation(Table.class).name());
  }

  public String toSql() {
    String result = sql;

    if (!clauses.isEmpty()) {
      Iterator<Clause> iterator = clauses.iterator();
      Clause firstClause = iterator.next();
      result += " where " + firstClause.clause;

      while (iterator.hasNext()) {
        Clause andClause = iterator.next();
        result += " and " + andClause.clause;
      }
    }

    if (orderBy != null) result += " order by " + orderBy;
    if (forUpdate) result += " for update";
    if (forUpdate && noWait) result += " nowait";
    if (offsetString != null) result += offsetString;
    if (count) {
      result = "select count(*) from (" + result + ") as T";
    }

    return result;
  }

  public Map<String, Object> parameters() {
    return clauses.stream().filter(c -> c.value != null)
      .collect(toMap(Clause::parameterName, c -> {
        if (c.value instanceof Enum) {
          return ((Enum) c.value).name();
        }
        return c.value;
      }));
  }

  public QueryBuilder where(String clause, Object value) {
    return and(clause, value);
  }

  public QueryBuilder where(String clause) {
    return and(clause, null);
  }

  public QueryBuilder and(String clause, Object value) {
    clauses.add(new Clause(clause, value));
    return this;
  }

  public QueryBuilder and(String clause) {
    clauses.add(new Clause(clause, null));
    return this;
  }

  public QueryBuilder orderBy(String orderBy) {
    this.orderBy = orderBy;
    return this;
  }

  public QueryBuilder forUpdate() {
    this.forUpdate = true;
    return this;
  }

  public QueryBuilder count() {
    this.count = true;
    return this;
  }

  public QueryBuilder nowWait() {
    this.noWait = true;
    return this;
  }

  public QueryBuilder limit(long page, long limit) {
    this.offset = (page - 1) * limit;
    this.limit = limit;
    return this;
  }

  private static String columns(Class<?> modelClass) {
    return Stream.of(modelClass.getFields()).filter(f -> f.getAnnotation(Column.class) != null)
      .map(f -> f.getAnnotation(Column.class).name()).collect(joining(","));
  }

  static class Clause {

    static final Pattern paramRegex = Pattern.compile("^.*?:(\\w+).*$");

    private String clause;
    private Object value;

    Clause(String clause, Object value) {
      this.clause = clause;
      this.value = toJdbcSupportedType(value);
    }

    private static Object toJdbcSupportedType(Object value) {
      if (value instanceof Instant) {
        return new Timestamp(((Instant) value).toEpochMilli());
      }

      return value;
    }

    String parameterName() {
      Matcher matcher = paramRegex.matcher(clause);
      if (matcher.matches()) {
        return matcher.group(1);
      } else {
        throw new RuntimeException("Unable to extract parameter name from " + clause);
      }
    }
  }
}
