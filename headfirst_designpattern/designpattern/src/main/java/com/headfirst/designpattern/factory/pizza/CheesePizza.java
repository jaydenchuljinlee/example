package com.headfirst.designpattern.factory.pizza;

import com.headfirst.designpattern.factory.pizza.ingredient.PizzaIngredientFactory;

public class CheesePizza extends Pizza{
    PizzaIngredientFactory factory;

    public CheesePizza(PizzaIngredientFactory factory) {
        this.factory = factory;
    }

    public void prepare() {
        System.out.println("준비중..." + getName());
        dough = factory.createDough();
        sauce = factory.createSauce();
        cheese = factory.createCheese();
    }
}
