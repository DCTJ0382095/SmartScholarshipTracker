package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.Student;
import com.smartscholarship.model.ScholarshipCategory;
import com.smartscholarship.strategy.EligibilityStrategy;
import com.smartscholarship.strategy.MeritEligibilityStrategy;
import com.smartscholarship.strategy.FinancialAidEligibilityStrategy;
import com.smartscholarship.strategy.ExcellenceEligibilityStrategy;
import java.util.Set;

/**
 * Evaluates whether a student is eligible for a scholarship
 * by selecting and applying the appropriate eligibility
 * strategies based on the scholarship categories.
 */
public class EligibilityService {

    private final ClassificationService classificationService = new ClassificationService();

    /**
     * Determines whether a student is eligible for a scholarship
     * by applying all matching eligibility strategies.
     *
     * @param student the student to evaluate
     * @param scholarship the scholarship to evaluate
     * @return {@code true} if the student satisfies all applicable
     *         eligibility criteria; otherwise {@code false}
     */
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
