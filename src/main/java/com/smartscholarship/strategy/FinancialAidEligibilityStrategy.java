package com.smartscholarship.strategy;

import com.smartscholarship.model.Student;

public class FinancialAidEligibilityStrategy {
    @Override
    public boolean checkEligibility(Student student) {

        return student.getHouseholdIncome() <= 5000;

    }
}
