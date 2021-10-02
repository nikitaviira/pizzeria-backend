package com.pizzeria.pizzaapp.domain.pizza.repository;

import com.pizzeria.pizzaapp.db.BaseRepository;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCut;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaCutRepository extends BaseRepository<PizzaCut> {
  @Override
  protected Class<PizzaCut> modelClass() {
    return PizzaCut.class;
  }
}
