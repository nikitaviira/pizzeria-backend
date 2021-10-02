package com.pizzeria.pizzaapp.dto;

import java.util.List;

public class PizzaCreateRequestDto {
  public Long cutId;
  public Long crustId;
  public Long sizeId;

  public List<PizzaItemDto> pizzaItems;

  public static class PizzaItemDto {
    public Long ingredientId;
    public int amount;
  }
}
