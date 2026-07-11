package com.smartscholarship.controller;

import com.smartscholarship.model.ScholarshipCategory;
import com.smartscholarship.strategy.EligibilityStrategy;
import javafx.scene.control.TextField;
import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.service.APIService;
import com.smartscholarship.service.EligibilityService;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipExploreController {

    private Runnable refreshAction;
    private final APIService apiService = new APIService();
    private final EligibilityService eligibilityService = new EligibilityService();
    private final List<Scholarship> scholarships;

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

    public ScholarshipExploreController() {
        scholarships = new ArrayList<>();
        scholarships.addAll(apiService.fetchScholarships());
    }

    public List<Scholarship> getScholarships(){
        return scholarships;
    }
}