package com.headfirst.designpattern.singleton;

public enum ChocolateBoilerEnum {
    UNIQUE_INSTANCE;

    private boolean empty;
    private boolean boiled;
    private int gallon;

    ChocolateBoilerEnum() {
        empty = true;
        boiled = false;
        gallon = 0;
    }

    public void fill() {
        if (isEmpty()) {
            empty = true;
            boiled = false;
        }
    }

    public void drain() {
        if (!isEmpty() && isBoiled()) {
            empty = true;
        }
    }

    public void boil() {
        if (!isEmpty() && !isBoiled()) {
            boiled = true;
        }
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isBoiled() {
        return boiled;
    }
}
