package com.smartscholarship.controller;

import com.smartscholarship.model.ApplicationStatus;
import com.smartscholarship.model.ScholarshipApplication;
import com.smartscholarship.repository.ApplicationRepository;
import java.util.List;
import com.smartscholarship.observer.ApplicationStatusNotifier;
import com.smartscholarship.observer.StudentNotificationObserver;


public class AdminDemoController {


    private Runnable refreshAction;
    private final ApplicationStatusNotifier notifier;
    private final StudentNotificationObserver notificationObserver;

    public AdminDemoController() {
        notifier = new ApplicationStatusNotifier();
        notificationObserver = new StudentNotificationObserver();

        notifier.addObserver(notificationObserver);
    }


    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    public List<ScholarshipApplication> getPendingApplications() {
        return ApplicationRepository.getApplications().stream().filter(application ->
                application.getStatus() == ApplicationStatus.APPLIED
                || application.getStatus() == ApplicationStatus.UNDER_REVIEW
        ).toList();
    }

    public List<ScholarshipApplication> getApplicationHistory() {
        return ApplicationRepository.getApplications().stream().filter(application ->
                application.getStatus() == ApplicationStatus.APPROVED
                || application.getStatus() == ApplicationStatus.REJECTED
        ).toList();
    }

    public List<ScholarshipApplication> searchPendingApplications(String query) {
        if (query == null || query.isBlank()) {
            return getPendingApplications();
        }

        String keyword = query.trim().toLowerCase();

        return getPendingApplications().stream()
                .filter(application ->
                        application.getApplicant().getName()
                                .toLowerCase()
                                .contains(keyword)
                                || application.getScholarship().getTitle()
                                .toLowerCase()
                                .contains(keyword)
                                || application.getApplicationID()
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

    private void updateApplicationStatus(String applicationId, String newStatus) {
        ScholarshipApplication selectedApplication = null;
        for (ScholarshipApplication application : ApplicationRepository.getApplications()) {
            if (application.getApplicationID().equals(applicationId)) {
                selectedApplication = application;
                break;
            }
        }
        if (selectedApplication == null) {
            return;
        }
        switch (newStatus) {
            case "Approved" ->
                    selectedApplication.setStatus(ApplicationStatus.APPROVED);
            case "Rejected" ->
                    selectedApplication.setStatus(ApplicationStatus.REJECTED);
            case "Under Review" ->
                    selectedApplication.setStatus(ApplicationStatus.UNDER_REVIEW);
            default ->
                    selectedApplication.setStatus(ApplicationStatus.APPLIED);
        }

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
}