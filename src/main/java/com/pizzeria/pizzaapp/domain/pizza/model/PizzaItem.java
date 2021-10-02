package com.pizzeria.pizzaapp.domain.pizza.model;

import com.pizzeria.pizzaapp.db.IdAware;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "pizza_item")
public class PizzaItem implements IdAware {
  @Id
  @GeneratedValue
  @Column(name = "id")
  public Long id;

  @Column(name = "amount")
  public int amount;

  @Column(name = "pizza_id")
  public Long pizzaId;

  @Column(name = "ingredient_id")
  public Long ingredientId;

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }
}
