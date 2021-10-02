package com.pizzeria.pizzaapp.domain.pizza.model;

import com.pizzeria.pizzaapp.db.IdAware;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "pizza")
public class Pizza implements IdAware {
  @Id
  @GeneratedValue
  @Column(name = "id")
  public Long id;

  @Column(name = "crust_id")
  public Long crustId;

  @Column(name = "cut_id")
  public Long cutId;

  @Column(name = "size_id")
  public Long sizeId;

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }
}
