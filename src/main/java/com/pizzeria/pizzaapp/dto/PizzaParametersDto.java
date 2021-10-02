package com.pizzeria.pizzaapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class PizzaParametersDto {
  public List<Cut> cuts;
  public List<Crust> crusts;
  public List<Size> sizes;

  public static class Cut {
    public Long id;
    public String name;
    public String nameRu;

    public Cut() {
    }

    public Cut(Long id, String name, String nameRu) {
      this.id = id;
      this.name = name;
      this.nameRu = nameRu;
    }
  }

  public static class Crust {
    public Long id;
    public BigDecimal price;
    public String name;
    public String nameRu;

    public Crust() {
    }

    public Crust(Long id, BigDecimal price, String name, String nameRu) {
      this.id = id;
      this.price = price;
      this.name = name;
      this.nameRu = nameRu;
    }
  }

  public static class Size {
    public Long id;
    public BigDecimal price;
    public String name;
    public String nameRu;
    public int diameter;

    public Size() {
    }

    public Size(Long id, BigDecimal price, String name, String nameRu, int diameter) {
      this.id = id;
      this.price = price;
      this.name = name;
      this.nameRu = nameRu;
      this.diameter = diameter;
    }
  }
}
