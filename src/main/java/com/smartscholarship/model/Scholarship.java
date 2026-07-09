package com.smartscholarship.model;

public class Scholarship {
    private String title;
    private String description;
    private String fullDescription;

    private String award;
    private String deadline;

    private String sponsorName;
    private String sponsorUrl;

    private String detailUrl;
    private String applyUrl;

    private String awardType;

    private String requirements;
    private String gpaRequirement;
    private String majors;

    private String enrollmentLevel;
    private String geographicRestrictions;

    // Default Constructor
    public Scholarship() {
    }

    // Full Constructor
    public Scholarship(String title, String description, String fullDescription,
                       String award, String deadline,
                       String sponsorName, String sponsorUrl,
                       String detailUrl, String applyUrl,
                       String awardType,
                       String requirements, String gpaRequirement,
                       String majors, String enrollmentLevel,
                       String geographicRestrictions) {

        this.title = title;
        this.description = description;
        this.fullDescription = fullDescription;
        this.award = award;
        this.deadline = deadline;
        this.sponsorName = sponsorName;
        this.sponsorUrl = sponsorUrl;
        this.detailUrl = detailUrl;
        this.applyUrl = applyUrl;
        this.awardType = awardType;
        this.requirements = requirements;
        this.gpaRequirement = gpaRequirement;
        this.majors = majors;
        this.enrollmentLevel = enrollmentLevel;
        this.geographicRestrictions = geographicRestrictions;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorUrl() {
        return sponsorUrl;
    }

    public void setSponsorUrl(String sponsorUrl) {
        this.sponsorUrl = sponsorUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getGpaRequirement() {
        return gpaRequirement;
    }

    public void setGpaRequirement(String gpaRequirement) {
        this.gpaRequirement = gpaRequirement;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public String getEnrollmentLevel() {
        return enrollmentLevel;
    }

    public void setEnrollmentLevel(String enrollmentLevel) {
        this.enrollmentLevel = enrollmentLevel;
    }

    public String getGeographicRestrictions() {
        return geographicRestrictions;
    }

    public void setGeographicRestrictions(String geographicRestrictions) {
        this.geographicRestrictions = geographicRestrictions;
    }

    @Override
    public String toString() {
        return "Scholarship{" +
                "title='" + title + '\'' +
                ", award='" + award + '\'' +
                ", deadline='" + deadline + '\'' +
                ", sponsorName='" + sponsorName + '\'' +
                '}';
    }
}
