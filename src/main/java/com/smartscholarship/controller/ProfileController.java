package com.smartscholarship.controller;

import com.smartscholarship.model.Student;
import com.smartscholarship.util.CurrentStudent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 * Handles profile submission and validation for the student profile page.
 * The controller validates user input, creates a student profile and
 * stores it as the current logged-in student.
 */
public class ProfileController {

    /**
     * Validates the submitted profile information and saves the
     * student's profile if all inputs are valid.
     *
     * @param nameField student's name input field
     * @param ageSpinner student's age selector
     * @param gmailField student's Gmail address input field
     * @param mobileField student's mobile number input field
     * @param gpaField student's GPA input field
     * @param incomeField student's household income input field
     * @param privacyCheckBox privacy policy agreement checkbox
     * @param notificationCheckBox notification preference checkbox
     * @return {@code true} if the profile is successfully submitted;
     *         otherwise {@code false}
     */
    public boolean handleSubmit(
            TextField nameField,
            Spinner<Integer> ageSpinner,
            TextField gmailField,
            TextField mobileField,
            TextField gpaField,
            TextField incomeField,
            CheckBox privacyCheckBox,
            CheckBox notificationCheckBox
    ) {

        String name = nameField.getText().trim();
        String gmail = gmailField.getText().trim();
        String mobile = mobileField.getText().trim();
        String gpa = gpaField.getText().trim();
        String income = incomeField.getText().trim();

        if (name.isEmpty()
                || gmail.isEmpty()
                || mobile.isEmpty()
                || gpa.isEmpty()
                || income.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Incomplete Profile",
                    "Please complete all profile fields."
            );
            return false;
        }

        if (!privacyCheckBox.isSelected()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Privacy Policy Required",
                    "Please agree to the Privacy Policy before submitting."
            );
            return false;
        }

        try {
            double gpaValue = Double.parseDouble(gpa);

            if (gpaValue < 0.0 || gpaValue > 4.0) {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Invalid GPA",
                        "Please enter a GPA between 0.0 and 4.0."
                );
                return false;
            }

        } catch (NumberFormatException e) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid GPA",
                    "Please enter a valid numeric GPA."
            );
            return false;
        }

        try {
            double incomeValue = Double.parseDouble(income);

            if (incomeValue < 0) {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Invalid Income",
                        "Household income cannot be negative."
                );
                return false;
            }

        } catch (NumberFormatException e) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Income",
                    "Please enter a valid annual household income."
            );
            return false;
        }

        int age = ageSpinner.getValue();
        boolean notificationsEnabled = notificationCheckBox.isSelected();

        Student student = new Student();
        student.setStudentID("S001");
        student.setName(name);
        student.setGPA(Double.parseDouble(gpa));
        student.setHouseholdIncome(Double.parseDouble(income));
        student.setAge(age);
        student.setGmail(gmail);
        student.setMobile(mobile);
        CurrentStudent.setStudent(student);
        System.out.println("Student profile saved.");

        showAlert(
                Alert.AlertType.INFORMATION,
                "Profile Submitted",
                "Your profile has been submitted successfully."
        );
        return true;
    }

    /**
     * Displays an alert dialog to the user.
     *
     * @param type alert type
     * @param title alert title
     * @param message alert message
     */
    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}