package com.headfirst.designpattern.singleton;

public class ChocolateBoilerNormal extends ChocolateBoilerAbstract implements ChocolateBoilerInterface {
    private static ChocolateBoilerNormal uniqueInstance;

    private ChocolateBoilerNormal() {
        empty = true;
        boiled = false;
        gallon = 0;
    }

    public static ChocolateBoilerNormal getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ChocolateBoilerNormal();
        }

        return uniqueInstance;
    }

    @Override
    public ChocolateBoilerNormal getName() {
        return uniqueInstance;
    }
}
