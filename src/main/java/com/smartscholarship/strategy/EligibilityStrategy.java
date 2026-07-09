package com.smartscholarship.strategy;

import com.smartscholarship.model.Student;

public interface EligibilityStrategy {
    boolean checkEligibility(Student student);
}
