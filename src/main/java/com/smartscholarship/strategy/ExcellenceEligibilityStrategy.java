package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;

/**
 * Evaluates eligibility for excellence-based scholarships.
 *
 * The assignment requires evaluating based on GPA and
 * household income. Thus, excellence-specific criteria such as
 * leadership, extracurricular activities and community
 * service cannot be evaluated.
 */
public class ExcellenceEligibilityStrategy implements EligibilityStrategy {

    /**
     * Evaluates whether the student satisfies the eligibility
     * requirements for an excellence scholarship.
     *
     * @param student the student to evaluate
     * @param scholarship the scholarship to evaluate
     * @return {@code true} because excellence-specific criteria
     *         are not available in the current data model
     */
    @Override
    public boolean checkEligibility(Student student, Scholarship scholarship) {
        return true;
    }
}
