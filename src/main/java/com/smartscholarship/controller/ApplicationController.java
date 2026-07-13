package com.smartscholarship.controller;

import javafx.scene.control.TextField;

/**
 * Handles user interactions for the scholarship application page,
 * including search and status filter actions.
 */
public class ApplicationController {

    private Runnable refreshAction;

    /**
     * Sets the action used to refresh the application view.
     *
     * @param refreshAction the refresh callback
     */
    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    /**
     * Triggers a refresh of the application view after the search field changes.
     *
     * @param searchField the search input field
     */
    public void handleSearch(TextField searchField) {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    /**
     * Triggers a refresh of the application view after the selected
     * application status changes.
     *
     * @param status the selected application status
     */
    public void handleStatusFilter(String status) {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }


}