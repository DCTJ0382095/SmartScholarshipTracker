package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.util.RequirementParser;

public class FinancialAidEligibilityStrategy implements EligibilityStrategy {

    @Override
    public boolean checkEligibility(Student student, Scholarship scholarship) {
        String text = scholarship.getDescription() + " " + scholarship.getRequirements() + " " + scholarship.getFullDescription();
        Double incomeLimit = RequirementParser.extractIncomeRequirement(text);
        if (incomeLimit == null) {
            return true;
        }

        return student.getHouseholdIncome() <= incomeLimit;
    }
}
