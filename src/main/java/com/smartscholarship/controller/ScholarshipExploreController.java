package com.smartscholarship.controller;

import javafx.scene.control.TextField;

public class ScholarshipExploreController {

    private Runnable refreshAction;

    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    public void handleSearch(TextField searchField) {
        String keyword = searchField.getText().trim();

        System.out.println("Searching scholarship: " + keyword);

        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    public void handleCategoryFilter(String category) {
        System.out.println("Selected category: " + category);

        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    public void handleScholarshipClick(String scholarshipName) {
        System.out.println("Selected scholarship: " + scholarshipName);
    }
}