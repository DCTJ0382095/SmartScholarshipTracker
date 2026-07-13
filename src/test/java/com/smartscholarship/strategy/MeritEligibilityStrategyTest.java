package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MeritEligibilityStrategyTest {

    private final MeritEligibilityStrategy strategy =
            new MeritEligibilityStrategy();

    @ParameterizedTest
    @CsvSource({
            "3.80, GPA 3.50 required, true",
            "3.20, GPA 3.50 required, false",
            "4.00, GPA 4.00 required, true",
            "2.50, GPA 2.00 required, true"
    })
    void checkEligibility_variousGpa_expectedResult(
            double studentGpa,
            String requirement,
            boolean expected
    ) {

        Student student = new Student();
        student.setGPA(studentGpa);

        Scholarship scholarship = new Scholarship();
        scholarship.setDescription(requirement);
        scholarship.setRequirements("");
        scholarship.setFullDescription("");

        assertEquals(
                expected,
                strategy.checkEligibility(student, scholarship)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "'', true",
            "'No GPA requirement', true"
    })
    void checkEligibility_noGpaRequirement_returnsTrue(
            String description,
            boolean expected
    ) {

        Student student = new Student();
        student.setGPA(1.00);

        Scholarship scholarship = new Scholarship();
        scholarship.setDescription(description);
        scholarship.setRequirements("");
        scholarship.setFullDescription("");

        assertEquals(
                expected,
                strategy.checkEligibility(student, scholarship)
        );
    }
}