package com.headfirst.designpattern.singleton;

public abstract class ChocolateBoilerAbstract {
    protected boolean empty;
    protected boolean boiled;
    protected int gallon;

    public void increase() {
        gallon += 1;
        System.out.println("현재 갤런: " + gallon);
    }

    public void decrease() {
        gallon -= 1;
        System.out.println("현재 갤런: " + gallon);
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
