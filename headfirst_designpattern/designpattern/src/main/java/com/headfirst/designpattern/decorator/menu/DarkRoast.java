package com.headfirst.designpattern.decorator.menu;

import com.headfirst.designpattern.decorator.decorator.Beverage;

public class DarkRoast extends Beverage {
    public DarkRoast() {
        this.description = "다크 로스트";
    }

    @Override
    public double cost() {
        return 2.01;
    }
}
