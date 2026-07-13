package com.smartscholarship.model;

/**
 * Represents a student using the Smart Scholarship Tracker.
 * This class stores the student's personal information,
 * academic information and household income used for
 * scholarship eligibility evaluation.
 */
public class Student {
    private String studentID;
    private String name;
    private double gpa;
    private double householdIncome;
    private int age;
    private String gmail;
    private String mobile;

    /**
     * Creates an empty student object.
     */
    public Student() {}

    /**
     * Creates a student with the required profile information.
     *
     * @param studentID the student ID
     * @param name the student's name
     * @param gpa the student's GPA
     * @param householdIncome the student's annual household income
     */
    public Student(String studentID,
                   String name,
                   double gpa,
                   double householdIncome) {
        this.studentID = studentID;
        this.name = name;
        this.gpa = gpa;
        this.householdIncome = householdIncome;
    }

    public String getStudentID() {return studentID;}
    public void setStudentID(String studentID) {this.studentID = studentID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getGPA() {return gpa;}
    public void setGPA(double gpa) {this.gpa = gpa;}

    public double getHouseholdIncome() {return householdIncome;}
    public void setHouseholdIncome(double householdIncome) {this.householdIncome = householdIncome;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getGmail() {return gmail;}
    public void setGmail(String gmail) {this.gmail = gmail;}

    public String getMobile() {return mobile;}
    public void setMobile(String mobile) {this.mobile = mobile;}
}
