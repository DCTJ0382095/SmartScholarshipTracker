package com.smartscholarship.controller;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.service.APIService;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles scholarship exploration functions, including
 * searching, filtering and retrieving available scholarships.
 */
public class ScholarshipExploreController {

    private Runnable refreshAction;
    private final APIService apiService = new APIService();
    private final List<Scholarship> scholarships;

    /**
     * Creates the scholarship exploration controller and
     * loads available scholarships from the API service.
     */
    public ScholarshipExploreController() {
        scholarships = new ArrayList<>();
        scholarships.addAll(apiService.fetchScholarships());
    }

    /**
     * Sets the action used to refresh the scholarship view.
     *
     * @param refreshAction the refresh callback
     */
    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    /**
     * Handles scholarship search using the entered keyword.
     *
     * @param searchField the search input field
     */
    public void handleSearch(TextField searchField) {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    /**
     * Handles scholarship filtering based on the selected category.
     *
     * @param category the selected scholarship category
     */
    public void handleCategoryFilter(String category) {
        if (refreshAction != null) {
            refreshAction.run();
        }
    }

    /**
     * Handles the selection of a scholarship.
     *
     * @param scholarshipName the selected scholarship name
     */
    public void handleScholarshipClick(String scholarshipName) {
    }

    /**
     * Returns all available scholarships.
     *
     * @return list of scholarships
     */
    public List<Scholarship> getScholarships() {
        return scholarships;
    }


}