package com.company.services;

import com.company.models.*;

import java.util.UUID;

public interface CourseProgressService {
    void setAutoCheckService(AutoCheckService autoCheckService);

    void setEducationalMaterialService(EducationalMaterialService educationalMaterialService);

    void setAccountService(AccountService accountService);

    void signUpUser(UUID courseId,
                    UUID studentId);

    void setStartProgress(UUID courseId,
                          UUID studentId);

    void makePassedEducationalStep(UUID courseId,
                                   UUID studentId,
                                   UUID educationalStepId);

    void makeProcessedTestStep(UUID courseId,
                               UUID studentId,
                               UUID testStepId,
                               TestAnswer chosenTestAnswer);

    Step getCurrentStep(UUID courseId,
                        UUID studentId);

    void congratulateStudent(Student student,
                             Course course);

    CourseAction buildCourseAction(UUID courseId,
                                   UUID studentId,
                                   ActionType actionType,
                                   Integer score);
}
