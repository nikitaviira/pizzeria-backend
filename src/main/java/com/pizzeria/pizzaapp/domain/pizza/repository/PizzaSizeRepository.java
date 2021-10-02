package com.pizzeria.pizzaapp.domain.pizza.repository;

import com.pizzeria.pizzaapp.db.BaseRepository;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaSize;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaSizeRepository extends BaseRepository<PizzaSize> {
  @Override
  protected Class<PizzaSize> modelClass() {
    return PizzaSize.class;
  }
}
