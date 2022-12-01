package com.headfirst.designpattern.composite;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Menu extends MenuComponent{
    private List<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
    private String name;
    private String description;

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void add(MenuComponent menuComponent) {
        this.menuComponents.add(menuComponent);
    }

    public void remove(MenuComponent menuComponent) {
        this.menuComponents.remove(menuComponent);
    }

    public MenuComponent getChild(int i) {
        return this.menuComponents.get(i);
    }

    public void print() {
        System.out.println("\n" + getName());
        System.out.println(", " + getDescription());
        System.out.println("---------------------");

        for (MenuComponent menuComponent: menuComponents) {
            menuComponent.print();
        }
    }
}
