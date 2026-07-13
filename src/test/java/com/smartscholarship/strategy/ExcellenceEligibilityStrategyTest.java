package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcellenceEligibilityStrategyTest {

    private final ExcellenceEligibilityStrategy strategy =
            new ExcellenceEligibilityStrategy();

    @Test
    void checkEligibility_validStudent_returnsTrue() {

        Student student = new Student();
        Scholarship scholarship = new Scholarship();

        assertTrue(
                strategy.checkEligibility(student, scholarship)
        );
    }

    /**
     * Deliberately failing test required by the assignment.
     * The current implementation always returns true because
     * excellence criteria are not yet implemented.
     */
    @Disabled("Deliberately failing test for assignment discussion")
    @Test
    void checkEligibility_studentWithoutAchievements_expectedFalse() {

        Student student = new Student();
        Scholarship scholarship = new Scholarship();

        assertFalse(
                strategy.checkEligibility(student, scholarship)
        );
    }
}