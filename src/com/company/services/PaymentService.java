package com.company.services;

import com.company.models.Course;
import com.company.models.Student;

public interface PaymentService {
    void payForCourse(Student student,
                      Course course);

    // it is simplification of real process
    boolean checkIfCoursePaid();
}
