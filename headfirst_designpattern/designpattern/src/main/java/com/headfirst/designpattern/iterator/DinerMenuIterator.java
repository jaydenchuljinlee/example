package com.headfirst.designpattern.iterator;

import java.util.Iterator;

public class DinerMenuIterator implements Iterator<MenuItem> {
    private MenuItem[] list;
    private int position = 0;

    public DinerMenuIterator(MenuItem[] list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position < list.length && list[position] != null;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = list[position];
        position += 1;
        return menuItem;
    }

    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException("최소 하나 이상은 있어야합니다.");
        }

        if (list[position-1] != null) {
            if (list.length - 1 - (position - 1) >= 0)
                System.arraycopy(list, position - 1 + 1, list, position - 1, list.length - 1 - (position - 1));
            list[list.length-1] = null;
        }
    }
}
