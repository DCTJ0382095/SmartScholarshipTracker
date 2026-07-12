package com.smartscholarship.observer;

import com.smartscholarship.model.ApplicationStatus;
import com.smartscholarship.model.Student;

public final class ObserverManager {
    private static final ApplicationStatusNotifier NOTIFIER = new ApplicationStatusNotifier();
    private static final StudentNotificationObserver OBSERVER = new StudentNotificationObserver();

    static {
        NOTIFIER.addObserver(OBSERVER);
    }

    private ObserverManager() {
    }

    public static ApplicationStatusNotifier getNotifier() {
        return NOTIFIER;
    }

    public static StudentNotificationObserver getObserver() {
        return OBSERVER;
    }
}
