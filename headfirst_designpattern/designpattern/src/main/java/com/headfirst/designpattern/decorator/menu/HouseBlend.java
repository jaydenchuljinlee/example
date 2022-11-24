package com.headfirst.designpattern.decorator.menu;

import com.headfirst.designpattern.decorator.decorator.Beverage;

public class HouseBlend extends Beverage {

    public HouseBlend() {
        this.description = "하우스 블렌드 커피";
    }

    @Override
    public double cost() {
        return .89;
    }
}
