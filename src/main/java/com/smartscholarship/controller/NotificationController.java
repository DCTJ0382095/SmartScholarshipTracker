package com.smartscholarship.controller;

import javafx.scene.control.TextField;

public class NotificationController {

    private Runnable refreshAction;

    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    public void handleSearch(TextField searchField) {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    public void handleViewMore() {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }
}