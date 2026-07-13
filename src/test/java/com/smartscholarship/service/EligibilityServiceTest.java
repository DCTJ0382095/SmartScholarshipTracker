package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EligibilityServiceTest {

    private EligibilityService service;
    private Student student;
    private Scholarship scholarship;

    @BeforeEach
    void setUp() {
        service = new EligibilityService();

        student = new Student();
        student.setGPA(3.80);
        student.setHouseholdIncome(30000);

        scholarship = new Scholarship();
    }

    @Test
    void isEligible_meritRequirementMet_returnsTrue() {

        scholarship.setRequirements("Minimum GPA 3.50");

        assertTrue(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_meritRequirementNotMet_returnsFalse() {

        student.setGPA(2.50);

        scholarship.setRequirements("Minimum GPA 3.50");

        assertFalse(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_financialAidRequirementMet_returnsTrue() {

        scholarship.setRequirements("Household income below 50000");

        assertTrue(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_financialAidRequirementNotMet_returnsFalse() {

        student.setHouseholdIncome(80000);

        scholarship.setRequirements("Household income below 50000");

        assertFalse(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_excellenceScholarship_returnsTrue() {

        scholarship.setRequirements("Leadership and community service");

        assertTrue(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_multipleRequirementsSatisfied_returnsTrue() {

        scholarship.setRequirements(
                "Minimum GPA 3.50 and household income below 50000"
        );

        assertTrue(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_multipleRequirementsOneFails_returnsFalse() {

        student.setGPA(2.00);

        scholarship.setRequirements(
                "Minimum GPA 3.50 and household income below 50000"
        );

        assertFalse(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_uncategorizedScholarship_returnsTrue() {

        scholarship.setRequirements("Random text");

        assertTrue(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_emptyRequirements_returnsTrue() {

        scholarship.setRequirements("");

        assertTrue(service.isEligible(student, scholarship));
    }

    @Test
    void isEligible_nullRequirements_returnsTrue() {

        scholarship.setRequirements(null);

        assertDoesNotThrow(() ->
                service.isEligible(student, scholarship)
        );
    }
}