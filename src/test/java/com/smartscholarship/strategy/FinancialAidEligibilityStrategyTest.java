package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinancialAidEligibilityStrategyTest {

    private final FinancialAidEligibilityStrategy strategy =
            new FinancialAidEligibilityStrategy();

    @ParameterizedTest
    @CsvSource({
            "2500, Household income below RM3000, true",
            "3500, Household income below RM3000, false",
            "1000, Household income below RM1500, true",
            "2000, Household income below RM1500, false"
    })
    void checkEligibility_variousIncome_expectedResult(
            double income,
            String description,
            boolean expected
    ) {

        Student student = new Student();
        student.setHouseholdIncome(income);

        Scholarship scholarship = new Scholarship();
        scholarship.setDescription(description);
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
            "'No income requirement', true"
    })
    void checkEligibility_noIncomeRequirement_returnsTrue(
            String description,
            boolean expected
    ) {

        Student student = new Student();
        student.setHouseholdIncome(10000);

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