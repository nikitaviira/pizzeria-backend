package com.pizzeria.pizzaapp.domain.pizza.repository;

import com.pizzeria.pizzaapp.db.BaseRepository;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCut;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaItem;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaItemRepository extends BaseRepository<PizzaItem> {
  @Override
  protected Class<PizzaItem> modelClass() {
    return PizzaItem.class;
  }
}
