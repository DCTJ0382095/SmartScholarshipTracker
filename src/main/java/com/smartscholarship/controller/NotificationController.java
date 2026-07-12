package com.smartscholarship.controller;

import javafx.scene.control.TextField;

/**
 * Handles user interactions for the notification page,
 * including searching notifications and viewing
 * notification details.
 */
public class NotificationController {

    private Runnable refreshAction;

    /**
     * Sets the action used to refresh the notification view.
     *
     * @param refreshAction the refresh callback
     */
    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    /**
     * Handles the notification search action.
     *
     * @param searchField the search input field
     */
    public void handleSearch(TextField searchField) {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    /**
     * Handles the action for viewing additional
     * notification details.
     */
    public void handleViewMore() {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }
}