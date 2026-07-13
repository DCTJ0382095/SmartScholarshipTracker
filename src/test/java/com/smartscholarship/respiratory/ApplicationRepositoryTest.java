package com.smartscholarship.repository;

import com.smartscholarship.model.ScholarshipApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationRepositoryTest {

    @BeforeEach
    void clearRepository() {
        ApplicationRepository.getApplications().clear();
    }

    @Test
    void getApplications_initiallyEmpty_repositoryEmpty() {
        assertTrue(ApplicationRepository.getApplications().isEmpty());
    }

    @Test
    void addApplication_validApplication_repositorySizeIncreases() {
        ScholarshipApplication application = new ScholarshipApplication();

        ApplicationRepository.addApplication(application);

        assertEquals(1,
                ApplicationRepository.getApplications().size());
    }

    @Test
    void addApplication_multipleApplications_allStored() {

        ApplicationRepository.addApplication(new ScholarshipApplication());
        ApplicationRepository.addApplication(new ScholarshipApplication());

        assertEquals(2,
                ApplicationRepository.getApplications().size());
    }

    @Test
    void getApplications_addedApplication_sameObjectReturned() {

        ScholarshipApplication application =
                new ScholarshipApplication();

        ApplicationRepository.addApplication(application);

        assertSame(
                application,
                ApplicationRepository.getApplications().get(0)
        );
    }

    @Test
    void addApplication_nullApplication_nullStored() {

        ApplicationRepository.addApplication(null);

        assertNull(
                ApplicationRepository.getApplications().get(0)
        );
    }
    @Test
    void getApplications_sameReference_returnsSameList() {

        assertSame(
                ApplicationRepository.getApplications(),
                ApplicationRepository.getApplications()
        );
    }

    @Test
    void addApplication_threeApplications_repositoryContainsThree() {

        ApplicationRepository.addApplication(new ScholarshipApplication());
        ApplicationRepository.addApplication(new ScholarshipApplication());
        ApplicationRepository.addApplication(new ScholarshipApplication());

        assertEquals(
                3,
                ApplicationRepository.getApplications().size()
        );
    }

    @Test
    void getApplications_clearRepository_repositoryBecomesEmpty() {

        ApplicationRepository.addApplication(new ScholarshipApplication());

        ApplicationRepository.getApplications().clear();

        assertTrue(
                ApplicationRepository.getApplications().isEmpty()
        );
    }

    @Test
    void addApplication_sameObjectTwice_bothStored() {

        ScholarshipApplication application = new ScholarshipApplication();

        ApplicationRepository.addApplication(application);
        ApplicationRepository.addApplication(application);

        assertEquals(
                2,
                ApplicationRepository.getApplications().size()
        );
    }

    @Test
    void getApplications_afterAddingNullAndObject_bothExist() {

        ScholarshipApplication application = new ScholarshipApplication();

        ApplicationRepository.addApplication(null);
        ApplicationRepository.addApplication(application);

        assertNull(ApplicationRepository.getApplications().get(0));
        assertSame(application, ApplicationRepository.getApplications().get(1));
    }

}