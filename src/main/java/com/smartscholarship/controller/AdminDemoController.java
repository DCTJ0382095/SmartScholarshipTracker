package com.smartscholarship.controller;

import java.util.ArrayList;
import java.util.List;
import com.smartscholarship.observer.ApplicationStatusNotifier;
import com.smartscholarship.observer.StudentNotificationObserver;

public class AdminDemoController {

    private final List<AdminApplication> pendingApplications;
    private final List<AdminApplication> applicationHistory;

    private Runnable refreshAction;
    private final ApplicationStatusNotifier notifier;
    private final StudentNotificationObserver notificationObserver;

    public AdminDemoController() {
        pendingApplications = new ArrayList<>();
        applicationHistory = new ArrayList<>();
        notifier = new ApplicationStatusNotifier();
        notificationObserver = new StudentNotificationObserver();

        notifier.addObserver(notificationObserver);

        createDemoData();
    }

    private void createDemoData() {
        pendingApplications.add(
                new AdminApplication(
                        "User Name",
                        "Future Leaders Scholarship",
                        "APP-001",
                        "Pending",
                        "3 hr ago"
                )
        );

        pendingApplications.add(
                new AdminApplication(
                        "User Name",
                        "Academic Excellence Award",
                        "APP-002",
                        "Pending",
                        "7 hr ago"
                )
        );

        applicationHistory.add(
                new AdminApplication(
                        "User Name",
                        "Student Support Scholarship",
                        "APP-003",
                        "Approved",
                        "1 day ago"
                )
        );

        applicationHistory.add(
                new AdminApplication(
                        "User Name",
                        "Community Support Grant",
                        "APP-004",
                        "Rejected",
                        "2 days ago"
                )
        );
    }

    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    public List<AdminApplication> getPendingApplications() {
        return new ArrayList<>(pendingApplications);
    }

    public List<AdminApplication> getApplicationHistory() {
        return new ArrayList<>(applicationHistory);
    }

    public List<AdminApplication> searchPendingApplications(
            String query
    ) {
        if (query == null || query.isBlank()) {
            return getPendingApplications();
        }

        String keyword = query.trim().toLowerCase();

        return pendingApplications.stream()
                .filter(application ->
                        application.getUserName()
                                .toLowerCase()
                                .contains(keyword)
                                || application.getScholarshipName()
                                .toLowerCase()
                                .contains(keyword)
                                || application.getApplicationId()
                                .toLowerCase()
                                .contains(keyword)
                )
                .toList();
    }

    public void approveApplication(String applicationId) {
        updateApplicationStatus(
                applicationId,
                "Approved"
        );
    }

    public void declineApplication(String applicationId) {
        updateApplicationStatus(
                applicationId,
                "Rejected"
        );
    }

    private void updateApplicationStatus(
            String applicationId,
            String newStatus
    ) {
        AdminApplication selectedApplication = null;

        for (AdminApplication application : pendingApplications) {
            if (application.getApplicationId().equals(applicationId)) {
                selectedApplication = application;
                break;
            }
        }

        if (selectedApplication == null) {
            return;
        }

        pendingApplications.remove(selectedApplication);
        selectedApplication.setStatus(newStatus);
        selectedApplication.setTime("Just now");
        applicationHistory.add(0, selectedApplication);

        notifier.notifyObservers(
                "Application "
                        + applicationId
                        + " has been "
                        + newStatus
        );

        refreshView();
    }

    private void refreshView() {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }
    public StudentNotificationObserver getNotificationObserver() {
        return notificationObserver;
    }

    public static class AdminApplication {

        private final String userName;
        private final String scholarshipName;
        private final String applicationId;
        private String status;
        private String time;

        public AdminApplication(
                String userName,
                String scholarshipName,
                String applicationId,
                String status,
                String time
        ) {
            this.userName = userName;
            this.scholarshipName = scholarshipName;
            this.applicationId = applicationId;
            this.status = status;
            this.time = time;
        }

        public String getUserName() {
            return userName;
        }

        public String getScholarshipName() {
            return scholarshipName;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}