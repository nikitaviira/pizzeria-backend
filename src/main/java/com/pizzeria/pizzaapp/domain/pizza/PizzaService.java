package com.pizzeria.pizzaapp.domain.pizza;

import com.pizzeria.pizzaapp.domain.pizza.model.Pizza;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCrust;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaCut;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaItem;
import com.pizzeria.pizzaapp.domain.pizza.model.PizzaSize;
import com.pizzeria.pizzaapp.domain.pizza.repository.PizzaCrustRepository;
import com.pizzeria.pizzaapp.domain.pizza.repository.PizzaCutRepository;
import com.pizzeria.pizzaapp.domain.pizza.repository.PizzaItemRepository;
import com.pizzeria.pizzaapp.domain.pizza.repository.PizzaRepository;
import com.pizzeria.pizzaapp.domain.pizza.repository.PizzaSizeRepository;
import com.pizzeria.pizzaapp.dto.IngredientDto;
import com.pizzeria.pizzaapp.dto.PizzaCreateRequestDto;
import com.pizzeria.pizzaapp.dto.PizzaCreateResponseDto;
import com.pizzeria.pizzaapp.dto.PizzaParametersDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.pizzeria.pizzaapp.domain.pizza.PizzaMapper.toPizza;
import static com.pizzeria.pizzaapp.domain.pizza.PizzaMapper.toPizzaItem;
import static com.pizzeria.pizzaapp.domain.pizza.PizzaMapper.toPizzaParametersDto;
import static com.pizzeria.pizzaapp.util.Translation.getCurrentLocale;
import static java.util.stream.Collectors.toList;

@Service
public class PizzaService {
  private final PizzaCrustRepository pizzaCrustRepository;
  private final PizzaCutRepository pizzaCutRepository;
  private final PizzaSizeRepository pizzaSizeRepository;
  private final PizzaItemRepository pizzaItemRepository;
  private final PizzaRepository pizzaRepository;

  public PizzaService(PizzaCrustRepository pizzaCrustRepository, PizzaCutRepository pizzaCutRepository,
                      PizzaSizeRepository pizzaSizeRepository, PizzaItemRepository pizzaItemRepository,
                      PizzaRepository pizzaRepository) {
    this.pizzaCrustRepository = pizzaCrustRepository;
    this.pizzaCutRepository = pizzaCutRepository;
    this.pizzaSizeRepository = pizzaSizeRepository;
    this.pizzaItemRepository = pizzaItemRepository;
    this.pizzaRepository = pizzaRepository;
  }

  public PizzaParametersDto pizzaCreationParameters() {
    List<PizzaCrust> crusts = pizzaCrustRepository.all();
    List<PizzaCut> cuts = pizzaCutRepository.all();
    List<PizzaSize> sizes = pizzaSizeRepository.all();

    return toPizzaParametersDto(crusts, cuts, sizes);
  }

  public Map<String, List<IngredientDto>> categorizedIngredients() {
    Locale currentLocale = getCurrentLocale();
    return pizzaRepository.categorizedIngredients(currentLocale);
  }

  public PizzaCreateResponseDto createPizza(PizzaCreateRequestDto request) {
    Pizza pizza = pizzaRepository.save(toPizza(request));

    List<PizzaItem> pizzaItems = request.pizzaItems.stream()
      .map(pizzaItem -> toPizzaItem(pizza.id, pizzaItem))
      .collect(toList());
    pizzaItemRepository.batchInsert(pizzaItems);

    return new PizzaCreateResponseDto(pizza.id);
  }
}
