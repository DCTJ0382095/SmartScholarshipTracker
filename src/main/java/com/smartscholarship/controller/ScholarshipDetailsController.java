package com.smartscholarship.controller;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.ApplicationStatus;
import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.ScholarshipApplication;
import com.smartscholarship.model.Student;
import com.smartscholarship.repository.ApplicationRepository;
import com.smartscholarship.util.CurrentStudent;

import java.util.UUID;
import javafx.scene.control.Alert;

import java.awt.Desktop;
import java.net.URI;

public class ScholarshipDetailsController {

    public void handleContact(String contactUrl) {
        if (contactUrl == null || contactUrl.isBlank()) {
            showMessage(
                    "Contact Unavailable",
                    "No contact information is available for this scholarship."
            );
            return;
        }

        openUrl(contactUrl);
    }

    public void handleApply(Scholarship scholarship) {
        Student student = CurrentStudent.getStudent();
        if (student == null) {
            showMessage("Profile Required", "Please complete your profile before applying.");
            return;
        }
        boolean alreadyApplied = ApplicationRepository.getApplications()
                .stream()
                .anyMatch(app ->
                        app.getApplicant().getStudentID().equals(student.getStudentID())
                                && app.getScholarship().getTitle().equals(scholarship.getTitle())
                );
        if (alreadyApplied) {
            showMessage("Already Applied", "You have already applied for this scholarship.");
            return;
        }
        ScholarshipApplication application = new ScholarshipApplication();
        application.setApplicationID(UUID.randomUUID().toString());
        application.setApplicant(student);
        application.setScholarship(scholarship);
        application.setStatus(ApplicationStatus.APPLIED);
        ApplicationRepository.addApplication(application);
        openUrl(scholarship.getApplyUrl());
    }

    private void openUrl(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                showMessage(
                        "Unable to Open Link",
                        "This device cannot open the link automatically."
                );
            }
        } catch (Exception exception) {
            showMessage(
                    "Unable to Open Link",
                    "The link could not be opened."
            );
        }
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}