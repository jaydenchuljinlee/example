package com.headfirst.designpattern.decorator.menu.condiment;

import com.headfirst.designpattern.decorator.decorator.Beverage;
import com.headfirst.designpattern.decorator.decorator.CondimentDecorator;

public class Mocha extends CondimentDecorator {

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + .2;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 모카";
    }
}
