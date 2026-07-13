package com.smartscholarship.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receives and stores application status notifications
 * for a student.
 */
public class StudentNotificationObserver implements Observer {

    private final List<String> notifications;

    /**
     * Creates a new student notification observer.
     */
    public StudentNotificationObserver() {
        this.notifications = new ArrayList<>();
    }

    /**
     * Receives a notification message and stores it.
     *
     * @param message the notification message
     */
    @Override
    public void update(String message) {
        notifications.add(message);
    }

    /**
     * Returns all stored notification messages.
     *
     * @return an unmodifiable list of notifications
     */
    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }
}