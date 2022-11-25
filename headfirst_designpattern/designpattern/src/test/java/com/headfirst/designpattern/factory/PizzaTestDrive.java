package com.headfirst.designpattern.factory;

import com.headfirst.designpattern.factory.pizza.Pizza;
import com.headfirst.designpattern.factory.store.NYPizzaStore;
import com.headfirst.designpattern.factory.store.PizzaStore;
import org.junit.jupiter.api.Test;

public class PizzaTestDrive {
    @Test
    public void 최첨단_피자_코드_테스트() {
        PizzaStore nyStore = new NYPizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("에단이 주문한 " + pizza + "\n");
    }
}
