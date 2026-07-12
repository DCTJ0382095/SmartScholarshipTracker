package com.smartscholarship.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequirementParser {

    //This method is used to extract the minimum GPA requirement from the scholarship information
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

    //This method is used to extract the household income requirement from the scholarship information
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
