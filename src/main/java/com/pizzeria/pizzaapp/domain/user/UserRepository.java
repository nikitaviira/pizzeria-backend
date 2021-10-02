package com.pizzeria.pizzaapp.domain.user;

import com.pizzeria.pizzaapp.db.BaseRepository;
import com.pizzeria.pizzaapp.db.QueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {

  public Optional<User> findByEmail(String email) {
    QueryBuilder builder = new QueryBuilder(modelClass())
      .where("lower(email) = :email", email.toLowerCase());
    return jdbcTemplate.query(builder.toSql(), builder.parameters(), modelOperations.getRowMapper())
      .stream().findAny();
  }

  @Override
  protected Class<User> modelClass() {
    return User.class;
  }
}
