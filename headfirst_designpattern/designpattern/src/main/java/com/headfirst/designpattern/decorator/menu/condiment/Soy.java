package com.headfirst.designpattern.decorator.menu.condiment;

import com.headfirst.designpattern.decorator.decorator.Beverage;
import com.headfirst.designpattern.decorator.decorator.CondimentDecorator;

public class Soy extends CondimentDecorator {

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + .29;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 두유";
    }
}
