package com.smartscholarship.util;

import com.smartscholarship.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentStudentTest {

    @BeforeEach
    void resetCurrentStudent() {
        CurrentStudent.setStudent(null);
    }

    @Test
    void getStudent_initiallyNull_returnsNull() {

        assertNull(CurrentStudent.getStudent());
    }

    @Test
    void setStudent_validStudent_studentStored() {

        Student student = new Student();
        student.setStudentID("S001");

        CurrentStudent.setStudent(student);

        assertSame(student, CurrentStudent.getStudent());
    }

    @Test
    void hasProfile_studentExists_returnsTrue() {

        CurrentStudent.setStudent(new Student());

        assertTrue(CurrentStudent.hasProfile());
    }

    @Test
    void hasProfile_noStudent_returnsFalse() {

        CurrentStudent.setStudent(null);

        assertFalse(CurrentStudent.hasProfile());
    }

    @Test
    void setStudent_replaceStudent_newStudentReturned() {

        Student first = new Student();
        Student second = new Student();

        CurrentStudent.setStudent(first);
        CurrentStudent.setStudent(second);

        assertSame(second, CurrentStudent.getStudent());
    }
}