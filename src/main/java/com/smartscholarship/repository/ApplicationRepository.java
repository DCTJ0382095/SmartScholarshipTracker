package com.smartscholarship.repository;

import com.smartscholarship.model.ScholarshipApplication;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository {

    private static final List<ScholarshipApplication> applications = new ArrayList<>();

    public static void addApplication(ScholarshipApplication application) {
        applications.add(application);
    }

    public static List<ScholarshipApplication> getApplications() {
        return applications;
    }
}
