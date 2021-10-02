package com.pizzeria.pizzaapp.domain.pizza.model;

import com.pizzeria.pizzaapp.db.IdAware;
import com.pizzeria.pizzaapp.db.LocalizedName;
import com.pizzeria.pizzaapp.util.Translation;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Locale;

import static com.pizzeria.pizzaapp.util.Translation.RUSSIAN;

@Table(name = "cut")
public class PizzaCut implements IdAware {
  @Id
  @GeneratedValue
  @Column(name = "id")
  public Long id;

  @Column(name = "name")
  public String name;

  @Column(name = "name_ru")
  public String nameRu;

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }
}
