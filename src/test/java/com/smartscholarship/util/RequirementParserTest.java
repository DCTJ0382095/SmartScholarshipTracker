package com.smartscholarship.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementParserTest {

    @Test
    void extractGPARequirement_validText_returnsGpa() {

        Double result = RequirementParser.extractGPARequirement(
                "Applicants must maintain a GPA of 3.50."
        );

        assertEquals(3.5, result);
    }

    @Test
    void extractGPARequirement_noRequirement_returnsNull() {

        Double result = RequirementParser.extractGPARequirement(
                "No GPA requirement."
        );

        assertNull(result);
    }

    @Test
    void extractIncomeRequirement_validText_returnsIncome() {

        Double result = RequirementParser.extractIncomeRequirement(
                "Household income below 3000."
        );

        assertEquals(3000.0, result);
    }

    @Test
    void extractIncomeRequirement_nullText_returnsNull() {

        assertNull(
                RequirementParser.extractIncomeRequirement(null)
        );
    }

    @Test
    void extractIncomeRequirement_invalidText_returnsNull() {

        Double result = RequirementParser.extractIncomeRequirement(
                "No household income requirement."
        );

        assertNull(result);
    }
    @Test
    void extractGPARequirement_minimumCgpa_returnsValue() {

        Double result =
                RequirementParser.extractGPARequirement(
                        "Minimum 3.75 CGPA required"
                );

        assertEquals(3.75, result);
    }

    @Test
    void extractGPARequirement_gradePointAverage_returnsValue() {

        Double result =
                RequirementParser.extractGPARequirement(
                        "Grade Point Average of 3.20"
                );

        assertEquals(3.20, result);
    }

    @Test
    void extractGPARequirement_upperCaseText_returnsValue() {

        Double result =
                RequirementParser.extractGPARequirement(
                        "MINIMUM GPA OF 3.60"
                );

        assertEquals(3.60, result);
    }

    @Test
    void extractIncomeRequirement_familyIncome_returnsValue() {

        Double result =
                RequirementParser.extractIncomeRequirement(
                        "Family income below 45000"
                );

        assertEquals(45000.0, result);
    }

    @Test
    void extractIncomeRequirement_annualIncome_returnsValue() {

        Double result =
                RequirementParser.extractIncomeRequirement(
                        "Annual income RM60000"
                );

        assertEquals(60000.0, result);
    }

    @Test
    void extractIncomeRequirement_incomeWithComma_returnsValue() {

        Double result =
                RequirementParser.extractIncomeRequirement(
                        "Household income RM120,000"
                );

        assertEquals(120000.0, result);
    }

    @Test
    void extractIncomeRequirement_upperCase_returnsValue() {

        Double result =
                RequirementParser.extractIncomeRequirement(
                        "HOUSEHOLD INCOME 30000"
                );

        assertEquals(30000.0, result);
    }

    @Test
    void extractIncomeRequirement_noIncome_returnsNull() {

        Double result =
                RequirementParser.extractIncomeRequirement(
                        "Leadership scholarship"
                );

        assertNull(result);
    }
}