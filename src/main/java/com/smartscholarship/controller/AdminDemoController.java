package com.smartscholarship.controller;

import com.smartscholarship.model.ApplicationStatus;
import com.smartscholarship.model.ScholarshipApplication;
import com.smartscholarship.observer.ApplicationStatusNotifier;
import com.smartscholarship.observer.StudentNotificationObserver;
import com.smartscholarship.repository.ApplicationRepository;

import java.util.List;

/**
 * Controls administrator operations for managing scholarship applications
 * and notifying students when the application status changes.
 */
public class AdminDemoController {

    private Runnable refreshAction;
    private final ApplicationStatusNotifier notifier;
    private final StudentNotificationObserver notificationObserver;

    /**
     * Creates the administrator controller and registers
     * the student notification observer.
     */
    public AdminDemoController() {
        notifier = new ApplicationStatusNotifier();
        notificationObserver = new StudentNotificationObserver();

        notifier.addObserver(notificationObserver);
    }

    /**
     * Sets the action used to refresh the user interface.
     *
     * @param refreshAction the refresh callback
     */
    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    /**
     * Returns all scholarship applications that are currently
     * pending review.
     *
     * @return list of pending scholarship applications
     */
    public List<ScholarshipApplication> getPendingApplications() {
        return ApplicationRepository.getApplications().stream()
                .filter(application ->
                        application.getStatus() == ApplicationStatus.APPLIED
                                || application.getStatus() == ApplicationStatus.UNDER_REVIEW
                )
                .toList();
    }

    /**
     * Returns all processed scholarship applications.
     *
     * @return list of approved and rejected scholarship applications
     */
    public List<ScholarshipApplication> getApplicationHistory() {
        return ApplicationRepository.getApplications().stream()
                .filter(application ->
                        application.getStatus() == ApplicationStatus.APPROVED
                                || application.getStatus() == ApplicationStatus.REJECTED
                )
                .toList();
    }

    /**
     * Searches pending scholarship applications using
     * the provided keyword.
     *
     * @param query the search keyword
     * @return matching scholarship applications
     */
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

    /**
     * Approves the specified scholarship application.
     *
     * @param applicationId the application ID
     */
    public void approveApplication(String applicationId) {
        updateApplicationStatus(applicationId, "Approved");
    }

    /**
     * Rejects the specified scholarship application.
     *
     * @param applicationId the application ID
     */
    public void declineApplication(String applicationId) {
        updateApplicationStatus(applicationId, "Rejected");
    }

    /**
     * Updates the application status and sends a notification
     * to all registered observers.
     */
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

    /**
     * Refreshes the user interface after data changes.
     */
    private void refreshView() {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    /**
     * Returns the notification observer used to receive
     * application status updates.
     *
     * @return the student notification observer
     */
    public StudentNotificationObserver getNotificationObserver() {
        return notificationObserver;
    }
}