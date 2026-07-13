package com.smartscholarship.observer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentNotificationObserverTest {

    @Test
    void constructor_newObserver_notificationListEmpty() {
        StudentNotificationObserver observer = new StudentNotificationObserver();

        assertTrue(observer.getNotifications().isEmpty());
    }

    @Test
    void update_singleNotification_notificationStored() {
        StudentNotificationObserver observer = new StudentNotificationObserver();

        observer.update("Application Approved");

        assertEquals(1, observer.getNotifications().size());
        assertEquals(
                "Application Approved",
                observer.getNotifications().get(0)
        );
    }

    @Test
    void update_multipleNotifications_allNotificationsStored() {
        StudentNotificationObserver observer = new StudentNotificationObserver();

        observer.update("Approved");
        observer.update("Rejected");
        observer.update("Submitted");

        assertEquals(3, observer.getNotifications().size());
    }

    @Test
    void getNotifications_modifyReturnedList_throwsException() {
        StudentNotificationObserver observer = new StudentNotificationObserver();

        observer.update("Approved");

        List<String> notifications = observer.getNotifications();

        assertThrows(
                UnsupportedOperationException.class,
                () -> notifications.add("New Notification")
        );
    }
}