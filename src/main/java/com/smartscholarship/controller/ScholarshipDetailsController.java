package com.smartscholarship.controller;

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

    public void handleApply(String applyUrl) {
        if (applyUrl == null || applyUrl.isBlank()) {
            showMessage(
                    "Application Unavailable",
                    "No application link is available for this scholarship."
            );
            return;
        }

        openUrl(applyUrl);
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