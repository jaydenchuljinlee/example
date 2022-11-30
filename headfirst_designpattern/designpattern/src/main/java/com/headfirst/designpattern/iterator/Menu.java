package com.headfirst.designpattern.iterator;

import java.util.Iterator;

public interface Menu {
    public Iterator<MenuItem> createIterator();
}
