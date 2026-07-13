package com.smartscholarship.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationControllerTest {

    @Test
    void setRefreshAction_handleSearch_refreshExecuted() {

        ApplicationController controller = new ApplicationController();

        final boolean[] executed = {false};

        controller.setRefreshAction(() -> executed[0] = true);

        controller.handleSearch(null);

        assertTrue(executed[0]);
    }

    @Test
    void handleSearch_withoutRefreshAction_noExceptionThrown() {

        ApplicationController controller = new ApplicationController();

        assertDoesNotThrow(() ->
                controller.handleSearch(null)
        );
    }

    @Test
    void handleStatusFilter_withRefreshAction_refreshExecuted() {

        ApplicationController controller = new ApplicationController();

        final boolean[] executed = {false};

        controller.setRefreshAction(() -> executed[0] = true);

        controller.handleStatusFilter("Approved");

        assertTrue(executed[0]);
    }

    @Test
    void handleStatusFilter_withoutRefreshAction_noExceptionThrown() {

        ApplicationController controller = new ApplicationController();

        assertDoesNotThrow(() ->
                controller.handleStatusFilter("Pending")
        );
    }

    @Test
    void setRefreshAction_multipleCalls_latestActionExecuted() {

        ApplicationController controller = new ApplicationController();

        final int[] counter = {0};

        controller.setRefreshAction(() -> counter[0]++);
        controller.setRefreshAction(() -> counter[0] += 2);

        controller.handleStatusFilter("Approved");

        assertEquals(2, counter[0]);
    }
}