package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.util.RequirementParser;

/**
 * Evaluates eligibility for financial aid scholarships
 * based on the student's household income.
 */
public class FinancialAidEligibilityStrategy implements EligibilityStrategy {

    /**
     * Determines whether a student satisfies the household
     * income requirement for a financial aid scholarship.
     *
     * @param student the student to evaluate
     * @param scholarship the scholarship to evaluate
     * @return {@code true} if the student's household income
     *         satisfies the scholarship requirement; otherwise
     *         {@code false}
     */
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
