package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.ScholarshipCategory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClassificationServiceTest {

    private final ClassificationService service =
            new ClassificationService();

    @Test
    void classify_meritScholarship_returnsMeritCategory() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("Academic Excellence Scholarship");
        scholarship.setDescription("Minimum GPA 3.50 required");
        scholarship.setFullDescription("");
        scholarship.setRequirements("");
        scholarship.setAwardType("");

        Set<ScholarshipCategory> result =
                service.classify(scholarship);

        assertTrue(result.contains(ScholarshipCategory.MERIT));
    }

    @Test
    void classify_financialAidScholarship_returnsFinancialAidCategory() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("Financial Assistance");
        scholarship.setDescription("Household income below RM3000");
        scholarship.setFullDescription("");
        scholarship.setRequirements("");
        scholarship.setAwardType("");

        Set<ScholarshipCategory> result =
                service.classify(scholarship);

        assertTrue(result.contains(ScholarshipCategory.FINANCIAL_AID));
    }

    @Test
    void classify_excellenceScholarship_returnsExcellenceCategory() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("Leadership Award");
        scholarship.setDescription("Outstanding leadership and community service");
        scholarship.setFullDescription("");
        scholarship.setRequirements("");
        scholarship.setAwardType("");

        Set<ScholarshipCategory> result =
                service.classify(scholarship);

        assertTrue(result.contains(ScholarshipCategory.EXCELLENCE));
    }

    @Test
    void classify_multipleCategories_returnsAllMatchingCategories() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("Academic Leadership Scholarship");
        scholarship.setDescription(
                "Minimum GPA 3.50 and household income below RM3000 with leadership experience"
        );
        scholarship.setFullDescription("");
        scholarship.setRequirements("");
        scholarship.setAwardType("");

        Set<ScholarshipCategory> result =
                service.classify(scholarship);

        assertAll(
                () -> assertTrue(result.contains(ScholarshipCategory.MERIT)),
                () -> assertTrue(result.contains(ScholarshipCategory.FINANCIAL_AID)),
                () -> assertTrue(result.contains(ScholarshipCategory.EXCELLENCE))
        );
    }

    @Test
    void classify_unknownScholarship_returnsUncategorized() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("Random Scholarship");
        scholarship.setDescription("Available for all students");
        scholarship.setFullDescription("");
        scholarship.setRequirements("");
        scholarship.setAwardType("");

        Set<ScholarshipCategory> result =
                service.classify(scholarship);

        assertEquals(1, result.size());
        assertTrue(result.contains(ScholarshipCategory.UNCATEGORIZED));
    }

    @Test
    void classify_caseInsensitiveKeywords_returnsCorrectCategory() {

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle("ACADEMIC SCHOLARSHIP");
        scholarship.setDescription("MINIMUM GPA 3.70");
        scholarship.setFullDescription("");
        scholarship.setRequirements("");
        scholarship.setAwardType("");

        Set<ScholarshipCategory> result =
                service.classify(scholarship);

        assertTrue(result.contains(ScholarshipCategory.MERIT));
    }
}