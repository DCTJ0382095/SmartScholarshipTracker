package com.smartscholarship.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StudentNotificationObserver implements Observer {

    private final List<String> notifications;


    public StudentNotificationObserver() {
        this.notifications = new ArrayList<>();
    }


    @Override
    public void update(String message) {
        notifications.add(message);
    }


    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }
}