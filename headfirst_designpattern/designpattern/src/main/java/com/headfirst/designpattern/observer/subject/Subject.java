package com.headfirst.designpattern.observer.subject;

import com.headfirst.designpattern.observer.observer.Observer;

public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
