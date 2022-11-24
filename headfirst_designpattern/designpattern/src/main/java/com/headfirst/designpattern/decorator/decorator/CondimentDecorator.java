package com.headfirst.designpattern.decorator.decorator;

public abstract class CondimentDecorator extends Beverage{
    public Beverage beverage;

    public abstract String getDescription();
}
