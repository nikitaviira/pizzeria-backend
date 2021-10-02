package com.pizzeria.pizzaapp.domain.pizza.repository;

import com.pizzeria.pizzaapp.db.BaseRepository;
import com.pizzeria.pizzaapp.domain.pizza.model.Pizza;
import com.pizzeria.pizzaapp.dto.IngredientDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.pizzeria.pizzaapp.domain.pizza.PizzaMapper.localizedFieldName;
import static com.pizzeria.pizzaapp.domain.pizza.PizzaMapper.toIngredientDto;

@Repository
public class PizzaRepository extends BaseRepository<Pizza> {

  public Map<String, List<IngredientDto>> categorizedIngredients(Locale locale) {
    Map<String, List<IngredientDto>> ingredientsByCategory = new LinkedHashMap<>();
    jdbcTemplate.query("SELECT i.id, i.name AS ingredient_name, i.name_ru AS ingredient_name_ru, "
        + "it.name AS ingredient_type_name, it.name_ru AS ingredient_type_name_ru, image_url, price "
        + "FROM ingredient_type it "
        + "INNER JOIN ingredient i ON it.id = i.ingredient_type_id "
        + "ORDER BY sort_order",
      Map.of(), rs -> {
        do {
          String localizedCategoryTypeName = rs.getString(localizedFieldName("ingredient_type_name", locale));
          var ingredient = toIngredientDto(rs);

          if (ingredientsByCategory.containsKey(localizedCategoryTypeName)) {
            ingredientsByCategory.get(localizedCategoryTypeName).add(ingredient);
          } else {
            ingredientsByCategory.put(localizedCategoryTypeName, new ArrayList<>(List.of(ingredient)));
          }
        } while (rs.next());
      });
    return ingredientsByCategory;
  }

  @Override
  protected Class<Pizza> modelClass() {
    return Pizza.class;
  }
}
