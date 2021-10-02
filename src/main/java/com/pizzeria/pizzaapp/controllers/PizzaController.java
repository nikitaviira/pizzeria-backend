package com.pizzeria.pizzaapp.controllers;

import com.pizzeria.pizzaapp.domain.pizza.PizzaService;
import com.pizzeria.pizzaapp.dto.IngredientDto;
import com.pizzeria.pizzaapp.dto.PizzaCreateRequestDto;
import com.pizzeria.pizzaapp.dto.PizzaCreateResponseDto;
import com.pizzeria.pizzaapp.dto.PizzaParametersDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PizzaController extends BaseController {
  private final PizzaService pizzaService;

  public PizzaController(PizzaService pizzaService) {
    this.pizzaService = pizzaService;
  }

  @GetMapping("/pizza/creation-parameters")
  public String pizzaParameters() {
    return "hui";
  }

  @PostMapping("/pizza/create")
  public PizzaCreateResponseDto pizzaParameters(@RequestBody PizzaCreateRequestDto pizzaCreateRequestDto) {
    return pizzaService.createPizza(pizzaCreateRequestDto);
  }

  @GetMapping("/pizza/ingredients-by-category")
  public Map<String, List<IngredientDto>> categorizedIngredients() {
    return pizzaService.categorizedIngredients();
  }
}

