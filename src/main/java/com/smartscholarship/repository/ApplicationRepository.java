package com.smartscholarship.repository;

import com.smartscholarship.model.ScholarshipApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores scholarship applications in memory and
 * provides access to the application list.
 */
public class ApplicationRepository {

    private static final List<ScholarshipApplication> applications = new ArrayList<>();

    /**
     * Adds a scholarship application to the repository.
     *
     * @param application the scholarship application to add
     */
    public static void addApplication(ScholarshipApplication application) {
        applications.add(application);
    }

    /**
     * Returns all stored scholarship applications.
     *
     * @return the list of scholarship applications
     */
    public static List<ScholarshipApplication> getApplications() {
        return applications;
    }
}
