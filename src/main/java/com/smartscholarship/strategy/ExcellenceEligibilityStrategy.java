package com.smartscholarship.strategy;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;

public class ExcellenceEligibilityStrategy implements EligibilityStrategy {

    @Override
    public boolean checkEligibility(Student student, Scholarship scholarship) {
        return true;
    }
}

//"Our Student model only stores GPA and household income.
// Excellence scholarships often require leadership, community service, research,
// or extracurricular achievements, but these attributes are not available in the current data model.
// Therefore, the strategy cannot evaluate those criteria."