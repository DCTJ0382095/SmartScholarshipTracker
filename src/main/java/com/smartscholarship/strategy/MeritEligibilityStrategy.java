package com.smartscholarship.strategy;

import com.smartscholarship.model.Student;

public class MeritEligibilityStrategy implements EligibilityStrategy {
    @Override
    public boolean checkEligibility(Student student) {

        return student.getGPA() >= 3.50;

    }
}
