package com.smartscholarship.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all registered observers and broadcasts
 * application status notifications to them.
 */
public class ApplicationStatusNotifier {

    private final List<Observer> observers;

    /**
     * Creates a new application status notifier.
     */
    public ApplicationStatusNotifier() {
        this.observers = new ArrayList<>();
    }

    /**
     * Registers a new observer if it has not already been added.
     *
     * @param observer the observer to register
     */
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes a registered observer.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Sends a notification message to all registered observers.
     *
     * @param message the notification message
     */
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * Returns the total number of registered observers.
     *
     * @return the number of registered observers
     */
    public int getObserverCount() {
        return observers.size();
    }
}