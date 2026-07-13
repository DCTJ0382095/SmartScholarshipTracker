package com.smartscholarship.model;

/**
 * Represents a scholarship application submitted
 * by a student.
 */
public class ScholarshipApplication {

    private String applicationID;
    private Student applicant;
    private Scholarship scholarship;
    private ApplicationStatus status;

    /**
     * Creates an empty scholarship application.
     */
    public ScholarshipApplication() {}

    /**
     * Creates a scholarship application with all required information.
     *
     * @param applicationID the application ID
     * @param applicant the student submitting the application
     * @param scholarship the scholarship being applied for
     * @param status the current application status
     */
    public ScholarshipApplication(String applicationID,
                                  Student applicant,
                                  Scholarship scholarship,
                                  ApplicationStatus status) {
        this.applicationID = applicationID;
        this.applicant = applicant;
        this.scholarship = scholarship;
        this.status = status;
    }

    public String getApplicationID() {return applicationID;}
    public void setApplicationID(String applicationID) {this.applicationID = applicationID;}

    public Student getApplicant() {return applicant;}
    public void setApplicant(Student applicant) {this.applicant = applicant;}

    public Scholarship getScholarship() {return scholarship;}
    public void setScholarship(Scholarship scholarship) {this.scholarship = scholarship;}

    public ApplicationStatus getStatus() {return status;}
    public void setStatus(ApplicationStatus status) {this.status = status;}
}
