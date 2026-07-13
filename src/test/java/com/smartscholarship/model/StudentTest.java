package com.smartscholarship.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void defaultConstructor_settersAndGetters_valuesStoredCorrectly() {

        Student student = new Student();

        student.setStudentID("S001");
        student.setName("John");
        student.setGPA(3.85);
        student.setHouseholdIncome(2500);
        student.setAge(22);
        student.setGmail("john@gmail.com");
        student.setMobile("0123456789");

        assertAll(
                () -> assertEquals("S001", student.getStudentID()),
                () -> assertEquals("John", student.getName()),
                () -> assertEquals(3.85, student.getGPA()),
                () -> assertEquals(2500, student.getHouseholdIncome()),
                () -> assertEquals(22, student.getAge()),
                () -> assertEquals("john@gmail.com", student.getGmail()),
                () -> assertEquals("0123456789", student.getMobile())
        );
    }

    @Test
    void fullConstructor_validArguments_fieldsInitializedCorrectly() {

        Student student = new Student(
                "S002",
                "Alice",
                3.60,
                1800
        );

        assertEquals("S002", student.getStudentID());
        assertEquals("Alice", student.getName());
        assertEquals(3.60, student.getGPA());
        assertEquals(1800, student.getHouseholdIncome());
    }

    @Test
    void setAge_validAge_ageUpdated() {

        Student student = new Student();

        student.setAge(25);

        assertEquals(25, student.getAge());
    }

    @Test
    void setGmail_validEmail_emailUpdated() {

        Student student = new Student();

        student.setGmail("student@test.com");

        assertEquals("student@test.com", student.getGmail());
    }

    @Test
    void setMobile_validNumber_mobileUpdated() {

        Student student = new Student();

        student.setMobile("0198888888");

        assertEquals("0198888888", student.getMobile());
    }
}