package com.headfirst.designpattern.decorator.menu.condiment;

import com.headfirst.designpattern.decorator.decorator.Beverage;
import com.headfirst.designpattern.decorator.decorator.CondimentDecorator;

public class Whip extends CondimentDecorator {

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + .1;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 휘핑크림";
    }
}
