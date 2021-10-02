package com.pizzeria.pizzaapp.domain.pizza.model;

import com.pizzeria.pizzaapp.db.IdAware;
import com.pizzeria.pizzaapp.db.LocalizedName;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Locale;

import static com.pizzeria.pizzaapp.util.Translation.RUSSIAN;

@Table(name = "ingredient")
public class Ingredient implements IdAware, LocalizedName {
  @Id
  @GeneratedValue
  @Column(name = "id")
  public Long id;

  @Column(name = "image_url")
  public String imageUrl;

  @Column(name = "name")
  public String name;

  @Column(name = "name_ru")
  public String nameRu;

  @Column(name = "price")
  public BigDecimal price;

  @Column(name = "ingredient_type_id")
  public Long ingredientTypeId;

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public String getLocalizedName(Locale locale) {
    return locale.equals(RUSSIAN) ? nameRu : name;
  }
}
