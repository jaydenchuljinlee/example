package com.headfirst.designpattern.factory.store;

import com.headfirst.designpattern.factory.pizza.CheesePizza;
import com.headfirst.designpattern.factory.pizza.Pizza;
import com.headfirst.designpattern.factory.pizza.ingredient.NYPizzaIngredientFactory;
import com.headfirst.designpattern.factory.pizza.ingredient.PizzaIngredientFactory;

public class NYPizzaStore extends PizzaStore{
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory factory = new NYPizzaIngredientFactory();

        if ("cheese".equals(type)) {
            pizza = new CheesePizza(factory);
            pizza.setName("치즈 피자");
        }
        return pizza;
    }
}
