package com.smartscholarship.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationControllerTest {

    @Test
    void setRefreshAction_validRunnable_refreshActionExecuted() {

        NotificationController controller = new NotificationController();

        final boolean[] executed = {false};

        controller.setRefreshAction(() -> executed[0] = true);

        controller.handleSearch(null);

        assertTrue(executed[0]);
    }

    @Test
    void handleSearch_withRefreshAction_refreshExecuted() {

        NotificationController controller = new NotificationController();

        final int[] counter = {0};

        controller.setRefreshAction(() -> counter[0]++);

        controller.handleSearch(null);

        assertEquals(1, counter[0]);
    }

    @Test
    void handleSearch_withoutRefreshAction_noExceptionThrown() {

        NotificationController controller = new NotificationController();

        assertDoesNotThrow(() ->
                controller.handleSearch(null)
        );
    }

    @Test
    void handleViewMore_withRefreshAction_refreshExecuted() {

        NotificationController controller = new NotificationController();

        final int[] counter = {0};

        controller.setRefreshAction(() -> counter[0]++);

        controller.handleViewMore();

        assertEquals(1, counter[0]);
    }

    @Test
    void handleViewMore_withoutRefreshAction_noExceptionThrown() {

        NotificationController controller = new NotificationController();

        assertDoesNotThrow(controller::handleViewMore);
    }
}