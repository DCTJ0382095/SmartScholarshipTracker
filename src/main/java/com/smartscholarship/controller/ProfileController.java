package com.smartscholarship.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import com.smartscholarship.model.Student;
import com.smartscholarship.util.CurrentStudent;

public class ProfileController {

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