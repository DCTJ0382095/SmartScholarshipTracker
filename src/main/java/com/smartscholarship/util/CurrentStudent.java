package com.smartscholarship.util;

import com.smartscholarship.model.Student;

/**
 * Stores the currently active student profile during
 * the application's runtime.
 */
public class CurrentStudent {

    private static Student currentStudent;

    /**
     * Returns the currently active student.
     *
     * @return the current student, or {@code null} if no
     *         profile has been created
     */
    public static Student getStudent() {
        return currentStudent;
    }

    /**
     * Sets the currently active student.
     *
     * @param student the student to store
     */
    public static void setStudent(Student student) {
        currentStudent = student;
    }

    /**
     * Determines whether a student profile has been created.
     *
     * @return {@code true} if a current student exists;
     *         otherwise {@code false}
     */
    public static boolean hasProfile() {
        return currentStudent != null;
    }
}