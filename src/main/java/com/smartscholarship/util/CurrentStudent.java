package com.smartscholarship.util;

import com.smartscholarship.model.Student;

public class CurrentStudent {
    private static Student currentStudent;
    public static Student getStudent() {
        return currentStudent;
    }

    public static void setStudent(Student student) {
        currentStudent = student;
    }

    public static boolean hasProfile() {
        return currentStudent != null;
    }
}