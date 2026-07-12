package com.smartscholarship.observer;

/**
 * Defines the contract for classes that receive
 * application status notifications.
 */
public interface Observer {

    /**
     * Updates the observer with a notification message.
     *
     * @param message the notification message
     */
    void update(String message);
}
