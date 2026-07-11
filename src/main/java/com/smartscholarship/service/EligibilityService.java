package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.model.ScholarshipCategory;
import com.smartscholarship.strategy.EligibilityStrategy;
import com.smartscholarship.strategy.MeritEligibilityStrategy;
import com.smartscholarship.strategy.FinancialAidEligibilityStrategy;
import com.smartscholarship.strategy.ExcellenceEligibilityStrategy;
import java.util.Set;

public class EligibilityService {

    private final ClassificationService classificationService =
            new ClassificationService();

    //This method is used to evaluate whether a student is eligible for a scholarship.
    public boolean isEligible(Student student, Scholarship scholarship) {
        Set<ScholarshipCategory> categories = classificationService.classify(scholarship);
        for(ScholarshipCategory category : categories){
            EligibilityStrategy strategy = getStrategy(category);
            if(strategy == null) {
                continue;
            }
            if(!strategy.checkEligibility(student, scholarship)) {
                return false;
            }
        }
        return true;
    }
    //This method is used to retrieve the appropriate eligibility strategy based on the scholarship category
    private EligibilityStrategy getStrategy(ScholarshipCategory category){
        return switch (category) {
            case MERIT -> new MeritEligibilityStrategy();
            case FINANCIAL_AID -> new FinancialAidEligibilityStrategy();
            case EXCELLENCE -> new ExcellenceEligibilityStrategy();
            default -> null;
        };
    }
}
