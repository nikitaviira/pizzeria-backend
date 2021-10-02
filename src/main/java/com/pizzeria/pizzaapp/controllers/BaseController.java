package com.pizzeria.pizzaapp.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@RequestMapping("/api/pizzeria")
public abstract class BaseController {

}
