package com.company.services.impl;

import com.company.models.Course;
import com.company.models.Student;
import com.company.services.PaymentService;

public class PaymentServiceImpl implements PaymentService {


    @Override
    public void payForCourse(Student student,
                             Course course){
        System.out.println(student.getName()+" paid "+course.getPrice() + " for course "+course.getTitle());
    }

    // it is simplification of real process
    @Override
    public boolean checkIfCoursePaid(){
        return true;
    }
}
