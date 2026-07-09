package com.smartscholarship.strategy;

import com.smartscholarship.model.Student;

public class ExcellenceEligibilityStrategy {
    @Override
    public boolean checkEligibility(Student student) {

        return student.getGPA() >= 3.80 &&
                student.getHouseholdIncome() <= 8000;

    }
}
