package com.smartscholarship.controller;

import com.smartscholarship.model.*;
import com.smartscholarship.observer.ObserverManager;
import com.smartscholarship.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminDemoControllerTest {

    private AdminDemoController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminDemoController();
        ApplicationRepository.getApplications().clear();
    }

    private ScholarshipApplication createApplication(
            String id,
            String studentName,
            String scholarshipTitle,
            ApplicationStatus status
    ) {

        Student student = new Student();
        student.setStudentID(id);
        student.setName(studentName);

        Scholarship scholarship = new Scholarship();
        scholarship.setTitle(scholarshipTitle);

        ScholarshipApplication application = new ScholarshipApplication();
        application.setApplicationID(id);
        application.setApplicant(student);
        application.setScholarship(scholarship);
        application.setStatus(status);

        return application;
    }

    @Test
    void getPendingApplications_pendingApplications_returnsPendingOnly() {

        ApplicationRepository.addApplication(
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED));

        ApplicationRepository.addApplication(
                createApplication("A002", "Alice", "Merit", ApplicationStatus.UNDER_REVIEW));

        ApplicationRepository.addApplication(
                createApplication("A003", "Tom", "Merit", ApplicationStatus.APPROVED));

        List<ScholarshipApplication> result =
                controller.getPendingApplications();

        assertEquals(2, result.size());
    }

    @Test
    void getApplicationHistory_processedApplications_returnsHistoryOnly() {

        ApplicationRepository.addApplication(
                createApplication("A001", "John", "Merit", ApplicationStatus.APPROVED));

        ApplicationRepository.addApplication(
                createApplication("A002", "Alice", "Merit", ApplicationStatus.REJECTED));

        ApplicationRepository.addApplication(
                createApplication("A003", "Tom", "Merit", ApplicationStatus.APPLIED));

        List<ScholarshipApplication> result =
                controller.getApplicationHistory();

        assertEquals(2, result.size());
    }

    @Test
    void searchPendingApplications_existingStudent_returnsMatchingApplication() {

        ApplicationRepository.addApplication(
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED));

        List<ScholarshipApplication> result =
                controller.searchPendingApplications("john");

        assertEquals(1, result.size());
    }

    @Test
    void searchPendingApplications_blankQuery_returnsAllPendingApplications() {

        ApplicationRepository.addApplication(
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED));

        assertEquals(
                controller.getPendingApplications().size(),
                controller.searchPendingApplications("").size()
        );
    }

    @Test
    void approveApplication_pendingApplication_statusUpdated() {

        ScholarshipApplication application =
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED);

        ApplicationRepository.addApplication(application);

        controller.approveApplication("A001");

        assertEquals(ApplicationStatus.APPROVED, application.getStatus());
    }

    @Test
    void reviewApplication_pendingApplication_statusUpdated() {

        ScholarshipApplication application =
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED);

        ApplicationRepository.addApplication(application);

        controller.reviewApplication("A001");

        assertEquals(ApplicationStatus.UNDER_REVIEW, application.getStatus());
    }

    @Test
    void declineApplication_pendingApplication_statusUpdated() {

        ScholarshipApplication application =
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED);

        ApplicationRepository.addApplication(application);

        controller.declineApplication("A001");

        assertEquals(ApplicationStatus.REJECTED, application.getStatus());
    }

    @Test
    void approveApplication_invalidId_applicationStatusUnchanged() {

        ScholarshipApplication application =
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED);

        ApplicationRepository.addApplication(application);

        controller.approveApplication("INVALID");

        assertEquals(ApplicationStatus.APPLIED, application.getStatus());
    }

    @Test
    void approveApplication_notificationAdded_notificationListIncreased() {

        ScholarshipApplication application =
                createApplication(
                        "A001",
                        "John",
                        "Merit Scholarship",
                        ApplicationStatus.APPLIED);

        ApplicationRepository.addApplication(application);

        int before =
                ObserverManager.getObserver()
                        .getNotifications()
                        .size();

        controller.approveApplication("A001");

        int after =
                ObserverManager.getObserver()
                        .getNotifications()
                        .size();

        assertEquals(before + 1, after);
    }

    @Test
    void setRefreshAction_refreshActionSet_refreshExecuted() {

        ScholarshipApplication application =
                createApplication("A001", "John", "Merit", ApplicationStatus.APPLIED);

        ApplicationRepository.addApplication(application);

        final boolean[] refreshed = {false};

        controller.setRefreshAction(() -> refreshed[0] = true);

        controller.approveApplication("A001");

        assertTrue(refreshed[0]);
    }
}