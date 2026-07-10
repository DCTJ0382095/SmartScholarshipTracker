package com.smartscholarship.model;

public class ScholarshipApplication {
    private String applicationID;
    private Student applicant;
    private Scholarship scholarship;
    private ApplicationStatus status;

    //This is the default constructor
    public ScholarshipApplication() {}

    //This is the full constructor
    public ScholarshipApplication(String applicationID,
                                  Student applicant,
                                  Scholarship scholarship,
                                  ApplicationStatus status) {

        this.applicationID = applicationID;
        this.applicant = applicant;
        this.scholarship = scholarship;
        this.status = status;
    }

    //This is the getters and setters
    public String getApplicationID() {return applicationID;}
    public void setApplicationID(String applicationID) {this.applicationID = applicationID;}

    public Student getApplicant() {return applicant;}
    public void setApplicant(Student applicant) {this.applicant = applicant;}

    public Scholarship getScholarship() {return scholarship;}
    public void setScholarship(Scholarship scholarship) {this.scholarship = scholarship;}

    public ApplicationStatus getStatus() {return status;}
    public void setStatus(ApplicationStatus status) {this.status = status;}
}
