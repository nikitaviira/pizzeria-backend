package com.pizzeria.pizzaapp.domain.pizza;

import com.pizzeria.pizzaapp.domain.pizza.model.Pizza;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCrust;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCut;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaItem;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaSize;
import com.pizzeria.pizzaapp.dto.IngredientDto;
import com.pizzeria.pizzaapp.dto.PizzaCreateRequestDto;
import com.pizzeria.pizzaapp.dto.PizzaCreateRequestDto.PizzaItemDto;
import com.pizzeria.pizzaapp.dto.PizzaParametersDto;
import com.pizzeria.pizzaapp.dto.PizzaParametersDto.Crust;
import com.pizzeria.pizzaapp.dto.PizzaParametersDto.Cut;
import com.pizzeria.pizzaapp.dto.PizzaParametersDto.Size;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static com.pizzeria.pizzaapp.util.Translation.RUSSIAN;
import static java.util.stream.Collectors.toList;

public class PizzaMapper {
  private PizzaMapper() {

  }

  public static PizzaParametersDto toPizzaParametersDto(List<PizzaCrust> crusts, List<PizzaCut> cuts,
                                                        List<PizzaSize> sizes) {
    PizzaParametersDto pizzaParametersDto = new PizzaParametersDto();
    pizzaParametersDto.crusts = crusts.stream()
      .map(crust -> new Crust(crust.id, crust.price, crust.name, crust.nameRu))
      .collect(toList());
    pizzaParametersDto.cuts = cuts.stream()
      .map(cut -> new Cut(cut.id, cut.name, cut.nameRu))
      .collect(toList());
    pizzaParametersDto.sizes = sizes.stream()
      .map(size -> new Size(size.id, size.price, size.name, size.nameRu, size.diameter))
      .collect(toList());
    return pizzaParametersDto;
  }

  public static IngredientDto toIngredientDto(ResultSet rs) throws SQLException {
    IngredientDto ingredientDto = new IngredientDto();
    ingredientDto.id = rs.getLong("id");
    ingredientDto.imageUrl = rs.getString("image_url");
    ingredientDto.name = rs.getString("ingredient_name");
    ingredientDto.nameRu = rs.getString("ingredient_name_ru");
    ingredientDto.price = rs.getBigDecimal("price");
    return ingredientDto;
  }

  public static Pizza toPizza(PizzaCreateRequestDto request) {
    Pizza pizza = new Pizza();
    pizza.crustId = request.crustId;
    pizza.sizeId = request.sizeId;
    pizza.cutId = request.cutId;
    return pizza;
  }

  public static PizzaItem toPizzaItem(Long pizzaId, PizzaItemDto pizzaItemDto) {
    PizzaItem pizzaItem = new PizzaItem();
    pizzaItem.pizzaId = pizzaId;
    pizzaItem.amount = pizzaItemDto.amount;
    pizzaItem.ingredientId = pizzaItemDto.ingredientId;
    return pizzaItem;
  }

  public static String localizedFieldName(String field, Locale locale) {
    return locale.equals(RUSSIAN) ? field + "_" + locale : field;
  }
}
