package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.util.RequirementParser;

/**
 * Evaluates eligibility for merit-based scholarships
 * based on the student's GPA.
 */
public class MeritEligibilityStrategy implements EligibilityStrategy {

    /**
     * Determines whether a student satisfies the GPA
     * requirement for a merit scholarship.
     *
     * @param student the student to evaluate
     * @param scholarship the scholarship to evaluate
     * @return {@code true} if the student's GPA satisfies
     *         the scholarship requirement; otherwise
     *         {@code false}
     */
    @Override
    public boolean checkEligibility(Student student, Scholarship scholarship) {
        String text = scholarship.getDescription() + " " + scholarship.getRequirements() + " " + scholarship.getFullDescription();
        Double requiredGPA = RequirementParser.extractGPARequirement(text);
        if (requiredGPA == null) {
            return true;
        }
        return student.getGPA() >= requiredGPA;
    }
}
