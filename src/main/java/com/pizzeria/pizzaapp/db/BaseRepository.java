package com.pizzeria.pizzaapp.db;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public abstract class BaseRepository<E extends IdAware> implements InitializingBean {

  @Autowired
  ApplicationContext applicationContext;

  @Autowired
  public NamedParameterJdbcTemplate jdbcTemplate;

  public ModelOperations<E> modelOperations;

  protected final ResultSetExtractor<E> firstOrNullRowMapper = rs -> rs.next()
    ? modelOperations.getRowMapper().mapRow(rs, 1)
    : null;

  protected <T> ModelOperations<T> initOnModelOperations(Class<T> modelClass) {
    //noinspection unchecked
    ModelOperations onModelOperations = applicationContext.getBean(ModelOperations.class, modelClass);
    onModelOperations.init();
    return onModelOperations;
  }

  protected abstract Class<E> modelClass();

  @Override
  public void afterPropertiesSet() {
    modelOperations = initOnModelOperations(modelClass());
  }

  public E insert(E entity) {
    String insertSql = modelOperations.getInsertSql();
    Map<String, Object> params = modelOperations.getParametersForInsertOrUpdate(entity);

    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(insertSql, new MapSqlParameterSource(params), generatedKeyHolder);

    entity.setId(((Number) generatedKeyHolder.getKeys().get("id")).longValue());

    return entity;
  }

  public void delete(E entity) {
    String deleteSql = modelOperations.getDeleteSql();
    Map<String, Object> params = modelOperations.getParametersForInsertOrUpdate(entity);
    jdbcTemplate.update(deleteSql, new MapSqlParameterSource(params));
  }

  public void batchInsert(List<E> entities) {
    String insertSql = modelOperations.getInsertSql();
    Map<String, Object>[] params = modelOperations.getParametersForBatchInsertOrUpdate(entities);
    jdbcTemplate.batchUpdate(insertSql, params);
  }

  public E update(E entity) {
    String updateSql = modelOperations.getUpdateSql();
    Map<String, Object> params = modelOperations.getParametersForInsertOrUpdate(entity);
    jdbcTemplate.update(updateSql, params);

    return entity;
  }

  public E save(E entity) {
    if (entity.getId() != null) {
      return update(entity);
    } else {
      return insert(entity);
    }
  }

  public E getById(Long id) {
    QueryBuilder builder = new QueryBuilder(modelClass())
      .where("id = :id", id);

    return jdbcTemplate.queryForObject(builder.toSql(), builder.parameters(), modelOperations.getRowMapper());
  }

  public Optional<E> find(Long id) {
    QueryBuilder builder = new QueryBuilder(modelClass())
      .where("id = :id", id);

    return ofNullable(jdbcTemplate.query(builder.toSql(), builder.parameters(), firstOrNullRowMapper));
  }

  public List<E> all() {
    QueryBuilder builder = queryBuilder();
    return jdbcTemplate.query(builder.toSql(), Map.of(), modelOperations.getRowMapper());
  }

  public E lock(Long id) {
    QueryBuilder builder = new QueryBuilder(modelClass())
      .where("id = :id", id).forUpdate();

    return jdbcTemplate.queryForObject(builder.toSql(), builder.parameters(), modelOperations.getRowMapper());
  }

  public E lockNoWait(E entity) {
    QueryBuilder builder = new QueryBuilder(modelClass())
      .where("id = :id", entity.getId()).forUpdate().nowWait();

    return jdbcTemplate.queryForObject(builder.toSql(), builder.parameters(), modelOperations.getRowMapper());
  }

  public static Timestamp toSqlTimestamp(Instant from) {
    return new Timestamp(from.toEpochMilli());
  }

  protected List<E> query(QueryBuilder queryBuilder) {
    return jdbcTemplate.query(queryBuilder.toSql(), queryBuilder.parameters(), modelOperations.getRowMapper());
  }

  protected QueryBuilder queryBuilder() {
    return new PostgresqlQueryBuilder(modelClass());
  }

  protected QueryBuilder queryBuilder(String sql) {
    return new PostgresqlQueryBuilder(sql);
  }

  protected QueryBuilder queryBuilderSelect(String select) {
    return new PostgresqlQueryBuilder(select, modelClass());
  }

  protected static <N extends Enum<N>> Set<String> stringifyEnumSet(N[] enumSet, Predicate<N> predicate) {
    return Arrays.stream(enumSet).filter(predicate).map(Enum::name).collect(toSet());
  }
}
