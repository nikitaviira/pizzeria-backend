package com.pizzeria.pizzaapp.domain.pizza.repository;

import com.pizzeria.pizzaapp.db.BaseRepository;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCrust;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaCrustRepository extends BaseRepository<PizzaCrust> {
  @Override
  protected Class<PizzaCrust> modelClass() {
    return PizzaCrust.class;
  }
}
