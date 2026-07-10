package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.util.RequirementParser;

public class MeritEligibilityStrategy implements EligibilityStrategy {

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
