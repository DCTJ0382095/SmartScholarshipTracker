package com.smartscholarship.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utility methods for extracting scholarship
 * eligibility requirements from scholarship descriptions
 * and requirement text.
 */
public class RequirementParser {

    /**
     * Extracts the minimum GPA requirement from scholarship
     * information.
     *
     * @param text the scholarship description and requirements
     * @return the required GPA, or {@code null} if no GPA
     *         requirement is found
     */
    public static Double extractGPARequirement(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        String[] patterns = {
                "minimum\\s+gpa\\s*(?:of|:)?\\s*(\\d(?:\\.\\d+)?)",
                "minimum\\s+(\\d(?:\\.\\d+)?)\\s+gpa",
                "minimum\\s+(\\d(?:\\.\\d+)?)\\s+cgpa",
                "at\\s+least\\s+(\\d(?:\\.\\d+)?)\\s+gpa",
                "maintain\\s+(?:a\\s+)?gpa\\s*(?:of)?\\s*(\\d(?:\\.\\d+)?)",
                "gpa\\s*(?:of|:)?\\s*(\\d(?:\\.\\d+)?)",
                "(\\d(?:\\.\\d+)?)\\s+cumulative\\s+gpa",
                "grade\\s+point\\s+average\\s*(?:of|:)?\\s*(\\d(?:\\.\\d+)?)"
        };
        for (String regex : patterns) {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        }
        return null;
    }

    /**
     * Extracts the household income requirement from
     * scholarship information.
     *
     * @param text the scholarship description and requirements
     * @return the household income limit, or {@code null}
     *         if no income requirement is found
     */
    public static Double extractIncomeRequirement(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        String[] patterns = {
                "household\\s+income.*?(\\d[\\d,]*)",
                "family\\s+income.*?(\\d[\\d,]*)",
                "annual\\s+income.*?(\\d[\\d,]*)",
                "income.*?(\\d[\\d,]*)"
        };
        for (String regex : patterns) {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String number = matcher.group(1).replace(",", "");
                return Double.parseDouble(number);
            }
        }
        return null;
    }
}
