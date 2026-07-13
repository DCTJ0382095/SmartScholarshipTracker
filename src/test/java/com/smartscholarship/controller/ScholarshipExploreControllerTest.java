package com.smartscholarship.controller;

import com.smartscholarship.model.Scholarship;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipExploreControllerTest {


    @Test
    void handleCategoryFilter_withRefreshAction_refreshExecuted() {

        ScholarshipExploreController controller =
                new ScholarshipExploreController();

        final boolean[] refreshed = {false};

        controller.setRefreshAction(() -> refreshed[0] = true);

        controller.handleCategoryFilter("Merit");

        assertTrue(refreshed[0]);
    }

    @Test
    void handleScholarshipClick_validScholarship_noExceptionThrown() {

        ScholarshipExploreController controller =
                new ScholarshipExploreController();

        assertDoesNotThrow(() ->
                controller.handleScholarshipClick("Taylor Scholarship")
        );
    }

    @Test
    void getScholarships_controllerCreated_returnsList() {

        ScholarshipExploreController controller =
                new ScholarshipExploreController();

        List<Scholarship> scholarships =
                controller.getScholarships();

        assertNotNull(scholarships);
    }

    @Test
    void getScholarships_multipleCalls_returnsSameList() {

        ScholarshipExploreController controller =
                new ScholarshipExploreController();

        assertSame(
                controller.getScholarships(),
                controller.getScholarships()
        );
    }

    @Test
    void handleCategoryFilter_withoutRefreshAction_noExceptionThrown() {

        ScholarshipExploreController controller =
                new ScholarshipExploreController();

        assertDoesNotThrow(() ->
                controller.handleCategoryFilter("Financial Aid")
        );
    }



}