package com.smartscholarship.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipTest {

    @Test
    void defaultConstructor_settersAndGetters_valuesStoredCorrectly() {

        Scholarship scholarship = new Scholarship();

        scholarship.setTitle("Taylor Scholarship");
        scholarship.setDescription("Academic scholarship");
        scholarship.setFullDescription("Awarded to outstanding students");
        scholarship.setAward("RM10000");
        scholarship.setDeadline("31 Dec 2026");
        scholarship.setSponsorName("Taylor University");
        scholarship.setSponsorUrl("https://taylor.edu");
        scholarship.setDetailUrl("https://detail.com");
        scholarship.setApplyUrl("https://apply.com");
        scholarship.setAwardType("Merit");
        scholarship.setRequirements("Minimum GPA 3.50");
        scholarship.setGpaRequirement("3.50");
        scholarship.setMajors("Software Engineering");
        scholarship.setEnrollmentLevel("Undergraduate");
        scholarship.setGeographicRestrictions("Malaysia");

        assertAll(
                () -> assertEquals("Taylor Scholarship", scholarship.getTitle()),
                () -> assertEquals("Academic scholarship", scholarship.getDescription()),
                () -> assertEquals("Awarded to outstanding students", scholarship.getFullDescription()),
                () -> assertEquals("RM10000", scholarship.getAward()),
                () -> assertEquals("31 Dec 2026", scholarship.getDeadline()),
                () -> assertEquals("Taylor University", scholarship.getSponsorName()),
                () -> assertEquals("https://taylor.edu", scholarship.getSponsorUrl()),
                () -> assertEquals("https://detail.com", scholarship.getDetailUrl()),
                () -> assertEquals("https://apply.com", scholarship.getApplyUrl()),
                () -> assertEquals("Merit", scholarship.getAwardType()),
                () -> assertEquals("Minimum GPA 3.50", scholarship.getRequirements()),
                () -> assertEquals("3.50", scholarship.getGpaRequirement()),
                () -> assertEquals("Software Engineering", scholarship.getMajors()),
                () -> assertEquals("Undergraduate", scholarship.getEnrollmentLevel()),
                () -> assertEquals("Malaysia", scholarship.getGeographicRestrictions())
        );
    }

    @Test
    void fullConstructor_validArguments_fieldsInitializedCorrectly() {

        Scholarship scholarship = new Scholarship(
                "Merit Scholarship",
                "Description",
                "Full Description",
                "RM5000",
                "1 Jan 2027",
                "Sponsor",
                "https://sponsor.com",
                "https://detail.com",
                "https://apply.com",
                "Merit",
                "Minimum GPA 3.50",
                "3.50",
                "Computer Science",
                "Degree",
                "Malaysia"
        );

        assertEquals("Merit Scholarship", scholarship.getTitle());
        assertEquals("Description", scholarship.getDescription());
        assertEquals("Full Description", scholarship.getFullDescription());
        assertEquals("RM5000", scholarship.getAward());
        assertEquals("1 Jan 2027", scholarship.getDeadline());
        assertEquals("Sponsor", scholarship.getSponsorName());
        assertEquals("https://sponsor.com", scholarship.getSponsorUrl());
        assertEquals("https://detail.com", scholarship.getDetailUrl());
        assertEquals("https://apply.com", scholarship.getApplyUrl());
        assertEquals("Merit", scholarship.getAwardType());
        assertEquals("Minimum GPA 3.50", scholarship.getRequirements());
        assertEquals("3.50", scholarship.getGpaRequirement());
        assertEquals("Computer Science", scholarship.getMajors());
        assertEquals("Degree", scholarship.getEnrollmentLevel());
        assertEquals("Malaysia", scholarship.getGeographicRestrictions());
    }

    @Test
    void toString_validScholarship_containsImportantFields() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("Merit Scholarship");
        scholarship.setAward("RM10000");
        scholarship.setSponsorName("Taylor");

        String result = scholarship.toString();

        assertAll(
                () -> assertTrue(result.contains("Merit Scholarship")),
                () -> assertTrue(result.contains("RM10000")),
                () -> assertTrue(result.contains("Taylor"))
        );
    }

    @Test
    void setRequirements_newRequirement_requirementUpdated() {

        Scholarship scholarship = new Scholarship();

        scholarship.setRequirements("Minimum GPA 3.80");

        assertEquals(
                "Minimum GPA 3.80",
                scholarship.getRequirements()
        );
    }

    @Test
    void setAwardType_newAwardType_awardTypeUpdated() {

        Scholarship scholarship = new Scholarship();

        scholarship.setAwardType("Financial Aid");

        assertEquals(
                "Financial Aid",
                scholarship.getAwardType()
        );
    }
}