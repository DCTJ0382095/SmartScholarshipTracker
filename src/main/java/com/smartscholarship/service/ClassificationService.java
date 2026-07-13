package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;
import com.smartscholarship.model.ScholarshipCategory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classifies scholarships into one or more scholarship
 * categories based on their title, description,
 * and eligibility requirements.
 */
public class ClassificationService {

    /**
     * Classifies a scholarship into one or more scholarship categories
     * based on its title, description and eligibility requirements.
     *
     * @param scholarship the scholarship to classify
     * @return the set of matching scholarship categories
     */
    public Set<ScholarshipCategory> classify(Scholarship scholarship) {
        Set<ScholarshipCategory> categories = new LinkedHashSet<>();
        String text = getClassificationText(scholarship);
        if(containsKeyword(text, MERIT_KEYWORDS)){
            categories.add(ScholarshipCategory.MERIT);
        }
        if(containsKeyword(text, FINANCIAL_AID_KEYWORDS)){
            categories.add(ScholarshipCategory.FINANCIAL_AID);
        }
        if(containsKeyword(text, EXCELLENCE_KEYWORDS)){
            categories.add(ScholarshipCategory.EXCELLENCE);
        }
        if(categories.isEmpty()){
            categories.add(ScholarshipCategory.UNCATEGORIZED);
        }
        return categories;
    }

    //This method is used to combine the scholarship information into a single string for matching keywords
    private String getClassificationText(Scholarship scholarship) {
        return (scholarship.getTitle() + " " +
                scholarship.getDescription() + " " +
                scholarship.getFullDescription() + " " +
                scholarship.getRequirements() + " " +
                scholarship.getAwardType()
        ).toLowerCase();
    }

    //This method is used to check if the scholarship contains any of the specified keywords.
    private boolean containsKeyword(String text, String... keywords) {
        for(String keyword : keywords){
            if(text.contains(keyword.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private static final String[] MERIT_KEYWORDS = {
            "gpa",
            "cgpa",
            "grade point average",
            "academic",
            "academic achievement",
            "academic excellence",
            "scholastic",
            "scholastic achievement",
            "honor",
            "honors",
            "honours",
            "dean",
            "dean's",
            "outstanding academic",
            "minimum gpa",
            "maintain a gpa",
            "cumulative gpa",
            "merit"
    };

    private static final String[] FINANCIAL_AID_KEYWORDS = {
            "financial need",
            "financial assistance",
            "financial hardship",
            "income",
            "low income",
            "household income",
            "family income",
            "annual income",
            "economic hardship",
            "b40",
            "means-tested",
            "need-based",
    };

    private static final String[] EXCELLENCE_KEYWORDS = {
            "leadership",
            "leader",
            "leadership qualities",
            "community service",
            "volunteer",
            "volunteerism",
            "research",
            "innovation",
            "creative",
            "art",
            "music",
            "athletic",
            "sport",
            "sports",
            "extracurricular",
            "activities",
            "character",
            "potential",
            "commitment",
            "service"
    };
}
