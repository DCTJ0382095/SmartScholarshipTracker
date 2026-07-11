package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;

public interface EligibilityStrategy {

    //This method is used to check whether a student satisfies the eligibility requirements of a scholarship
    boolean checkEligibility(Student student, Scholarship scholarship);
}
