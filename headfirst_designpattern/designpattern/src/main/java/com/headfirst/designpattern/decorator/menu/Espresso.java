package com.headfirst.designpattern.decorator.menu;

import com.headfirst.designpattern.decorator.decorator.Beverage;

public class Espresso extends Beverage {
    public Espresso() {
        this.description = "에스프레소";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
