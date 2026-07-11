package com.smartscholarship.model;

public class Student {
    private String studentID;
    private String name;
    private double gpa;
    private double householdIncome;

    //This is the default constructor
    public Student() {}

    //This is the full constructor
    public Student(String studentID,
                   String name,
                   double gpa,
                   double householdIncome) {
        this.studentID = studentID;
        this.name = name;
        this.gpa = gpa;
        this.householdIncome = householdIncome;
    }

    //This is the getters and setters
    public String getStudentID() {return studentID;}
    public void setStudentID(String studentID) {this.studentID = studentID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getGPA() {return gpa;}
    public void setGPA(double gpa) {this.gpa = gpa;}

    public double getHouseholdIncome() {return householdIncome;}
    public void setHouseholdIncome(double householdIncome) {this.householdIncome = householdIncome;}
}
