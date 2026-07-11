package com.smartscholarship.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import com.smartscholarship.model.Student;
import com.smartscholarship.util.CurrentStudent;

public class ProfileController {

    public void handleSubmit(
            TextField nameField,
            Spinner<Integer> ageSpinner,
            TextField gmailField,
            TextField mobileField,
            TextField schoolField,
            TextField fieldOfStudyField,
            TextField gpaField,
            TextField incomeField,
            CheckBox privacyCheckBox,
            CheckBox notificationCheckBox
    ) {

        String name = nameField.getText().trim();
        String gmail = gmailField.getText().trim();
        String mobile = mobileField.getText().trim();
        String school = schoolField.getText().trim();
        String fieldOfStudy = fieldOfStudyField.getText().trim();
        String gpa = gpaField.getText().trim();
        String income = incomeField.getText().trim();

        if (name.isEmpty()
                || gmail.isEmpty()
                || mobile.isEmpty()
                || school.isEmpty()
                || fieldOfStudy.isEmpty()
                || gpa.isEmpty()
                || income.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Incomplete Profile",
                    "Please complete all profile fields."
            );
            return;
        }

        if (!privacyCheckBox.isSelected()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Privacy Policy Required",
                    "Please agree to the Privacy Policy before submitting."
            );
            return;
        }

        try {
            double gpaValue = Double.parseDouble(gpa);

            if (gpaValue < 0.0 || gpaValue > 4.0) {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Invalid GPA",
                        "Please enter a GPA between 0.0 and 4.0."
                );
                return;
            }

        } catch (NumberFormatException e) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid GPA",
                    "Please enter a valid numeric GPA."
            );
            return;
        }

        try {
            double incomeValue = Double.parseDouble(income);

            if (incomeValue < 0) {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Invalid Income",
                        "Household income cannot be negative."
                );
                return;
            }

        } catch (NumberFormatException e) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Income",
                    "Please enter a valid annual household income."
            );
            return;
        }

        int age = ageSpinner.getValue();
        boolean notificationsEnabled = notificationCheckBox.isSelected();

        Student student = new Student();
        student.setStudentID("S001");
        student.setName(name);
        student.setGPA(Double.parseDouble(gpa));
        student.setHouseholdIncome(Double.parseDouble(income));
        CurrentStudent.setStudent(student);
        System.out.println("Student profile saved.");

        showAlert(
                Alert.AlertType.INFORMATION,
                "Profile Submitted",
                "Your profile has been submitted successfully."
        );
    }

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