package com.smartscholarship.controller;

import com.smartscholarship.model.ApplicationStatus;
import com.smartscholarship.model.ScholarshipApplication;
import com.smartscholarship.repository.ApplicationRepository;
import java.util.List;


public class AdminDemoController {

    private Runnable refreshAction;

    public AdminDemoController() {}


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
            case "Approved" -> selectedApplication.setStatus(ApplicationStatus.APPROVED);
            case "Rejected" -> selectedApplication.setStatus(ApplicationStatus.REJECTED);
            case "Under Review" -> selectedApplication.setStatus(ApplicationStatus.UNDER_REVIEW);
            default -> selectedApplication.setStatus(ApplicationStatus.APPLIED);
        }
        refreshView();
    }

    private void refreshView() {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }
}