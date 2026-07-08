package com.smartscholarship.observer;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStatusNotifier {

    private final List<Observer> observers;

    public ApplicationStatusNotifier() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }
}