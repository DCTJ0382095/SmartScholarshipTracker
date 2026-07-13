package com.smartscholarship.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationStatusNotifierTest {

    @Test
    void constructor_newNotifier_observerCountIsZero() {
        ApplicationStatusNotifier notifier = new ApplicationStatusNotifier();

        assertEquals(0, notifier.getObserverCount());
    }

    @Test
    void addObserver_validObserver_observerAdded() {
        ApplicationStatusNotifier notifier = new ApplicationStatusNotifier();
        StudentNotificationObserver observer = new StudentNotificationObserver();

        notifier.addObserver(observer);

        assertEquals(1, notifier.getObserverCount());
    }

    @Test
    void addObserver_duplicateObserver_notAddedTwice() {
        ApplicationStatusNotifier notifier = new ApplicationStatusNotifier();
        StudentNotificationObserver observer = new StudentNotificationObserver();

        notifier.addObserver(observer);
        notifier.addObserver(observer);

        assertEquals(1, notifier.getObserverCount());
    }

    @Test
    void addObserver_nullObserver_observerNotAdded() {
        ApplicationStatusNotifier notifier = new ApplicationStatusNotifier();

        notifier.addObserver(null);

        assertEquals(0, notifier.getObserverCount());
    }

    @Test
    void removeObserver_existingObserver_observerRemoved() {
        ApplicationStatusNotifier notifier = new ApplicationStatusNotifier();
        StudentNotificationObserver observer = new StudentNotificationObserver();

        notifier.addObserver(observer);
        notifier.removeObserver(observer);

        assertEquals(0, notifier.getObserverCount());
    }

    @Test
    void notifyObservers_registeredObserver_notificationReceived() {
        ApplicationStatusNotifier notifier = new ApplicationStatusNotifier();
        StudentNotificationObserver observer = new StudentNotificationObserver();

        notifier.addObserver(observer);

        notifier.notifyObservers("Application Approved");

        assertAll(
                () -> assertEquals(1, observer.getNotifications().size()),
                () -> assertEquals("Application Approved",
                        observer.getNotifications().get(0))
        );
    }
}