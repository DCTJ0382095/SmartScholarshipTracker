package com.smartscholarship.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipApplicationTest {

    @Test
    void defaultConstructor_settersAndGetters_valuesStoredCorrectly() {

        ScholarshipApplication application = new ScholarshipApplication();

        Student student = new Student();
        Scholarship scholarship = new Scholarship();

        application.setApplicationID("APP001");
        application.setApplicant(student);
        application.setScholarship(scholarship);
        application.setStatus(ApplicationStatus.APPLIED);

        assertAll(
                () -> assertEquals("APP001", application.getApplicationID()),
                () -> assertSame(student, application.getApplicant()),
                () -> assertSame(scholarship, application.getScholarship()),
                () -> assertEquals(ApplicationStatus.APPLIED, application.getStatus())
        );
    }

    @Test
    void fullConstructor_validArguments_fieldsInitializedCorrectly() {

        Student student = new Student();
        Scholarship scholarship = new Scholarship();

        ScholarshipApplication application =
                new ScholarshipApplication(
                        "APP002",
                        student,
                        scholarship,
                        ApplicationStatus.APPROVED
                );

        assertEquals("APP002", application.getApplicationID());
        assertSame(student, application.getApplicant());
        assertSame(scholarship, application.getScholarship());
        assertEquals(ApplicationStatus.APPROVED, application.getStatus());
    }

    @Test
    void setStatus_rejected_statusUpdated() {

        ScholarshipApplication application =
                new ScholarshipApplication();

        application.setStatus(ApplicationStatus.REJECTED);

        assertEquals(
                ApplicationStatus.REJECTED,
                application.getStatus()
        );
    }

    @Test
    void setApplicant_newStudent_applicantUpdated() {

        ScholarshipApplication application =
                new ScholarshipApplication();

        Student student = new Student();

        application.setApplicant(student);

        assertSame(student, application.getApplicant());
    }

    @Test
    void setScholarship_newScholarship_scholarshipUpdated() {

        ScholarshipApplication application =
                new ScholarshipApplication();

        Scholarship scholarship = new Scholarship();

        application.setScholarship(scholarship);

        assertSame(
                scholarship,
                application.getScholarship()
        );
    }
}