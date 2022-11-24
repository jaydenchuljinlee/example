package com.headfirst.designpattern.decorator;

import com.headfirst.designpattern.decorator.decorator.Beverage;
import com.headfirst.designpattern.decorator.menu.DarkRoast;
import com.headfirst.designpattern.decorator.menu.Espresso;
import com.headfirst.designpattern.decorator.menu.HouseBlend;
import com.headfirst.designpattern.decorator.menu.condiment.Mocha;
import com.headfirst.designpattern.decorator.menu.condiment.Soy;
import com.headfirst.designpattern.decorator.menu.condiment.Whip;
import org.junit.jupiter.api.Test;

public class StarbuzzCoffeTest {
    @Test
    public void 커피_주문_시스템_코드_테스트() {
        Beverage beverage1 = new Espresso();
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());

        Beverage beverage3 = new HouseBlend();
        beverage3 = new Mocha(beverage3);
        beverage3 = new Soy(beverage3);
        beverage3 = new Whip(beverage3);
        System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
    }
}
