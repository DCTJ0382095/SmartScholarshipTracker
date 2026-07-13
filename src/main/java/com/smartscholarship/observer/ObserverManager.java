package com.smartscholarship.observer;

/**
 * Provides shared access to the application's notification
 * manager and registered notification observer.
 */
public final class ObserverManager {

    private static final ApplicationStatusNotifier NOTIFIER = new ApplicationStatusNotifier();
    private static final StudentNotificationObserver OBSERVER = new StudentNotificationObserver();

    static {
        NOTIFIER.addObserver(OBSERVER);
    }

    /**
     * Prevents instantiation of this utility class.
     */
    private ObserverManager() {}

    /**
     * Returns the shared application status notifier.
     *
     * @return the application status notifier
     */
    public static ApplicationStatusNotifier getNotifier() {
        return NOTIFIER;
    }

    /**
     * Returns the shared student notification observer.
     *
     * @return the student notification observer
     */
    public static StudentNotificationObserver getObserver() { return OBSERVER; }
}
