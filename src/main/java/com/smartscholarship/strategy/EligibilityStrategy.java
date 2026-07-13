package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;

/**
 * Defines the contract for evaluating whether a student
 * is eligible for a scholarship.
 */
public interface EligibilityStrategy {

    /**
     * Determines whether a student satisfies the eligibility
     * requirements for a scholarship.
     *
     * @param student the student to evaluate
     * @param scholarship the scholarship to evaluate
     * @return {@code true} if the student is eligible;
     *         otherwise {@code false}
     */
    boolean checkEligibility(Student student, Scholarship scholarship);
}
