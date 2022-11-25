package com.headfirst.designpattern.singleton;

public class ChocolateBoilerSingleton extends ChocolateBoilerAbstract implements ChocolateBoilerInterface {
    public volatile static ChocolateBoilerSingleton uniqueInstance = new ChocolateBoilerSingleton();

    private ChocolateBoilerSingleton() {
        empty = true;
        boiled = false;
        gallon = 0;
    }

    public static ChocolateBoilerSingleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (ChocolateBoilerSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ChocolateBoilerSingleton();
                    System.out.println(uniqueInstance);
                }
            }
        }

        return uniqueInstance;
    }

    @Override
    public ChocolateBoilerSingleton getName() {
        return uniqueInstance;
    }
}
