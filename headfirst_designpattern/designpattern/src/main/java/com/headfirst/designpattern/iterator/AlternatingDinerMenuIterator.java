package com.headfirst.designpattern.iterator;

import java.util.Calendar;
import java.util.Iterator;

public class AlternatingDinerMenuIterator implements Iterator<Object> {
    private MenuItem[] items;
    private int position;

    public AlternatingDinerMenuIterator(MenuItem[] items) {
        this.items = items;
        this.position = Calendar.DAY_OF_WEEK % 2;
    }

    @Override
    public boolean hasNext() {
        return position < items.length && items[position] != null;
    }

    @Override
    public Object next() {
        MenuItem menuItem = items[position];
        position+=2;
        return menuItem;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("제거 메서드는 지원하지 않습니다.");
    }
}
