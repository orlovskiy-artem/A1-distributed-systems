package com.company.services.impl;

import com.company.services.EducationalMaterialService;
import com.company.services.CourseProgressService;
import com.company.models.*;
import com.company.services.AccountService;
import com.company.services.AutoCheckService;

import java.time.ZonedDateTime;
import java.util.*;

public class CourseProgressServiceImpl implements CourseProgressService {
    // this format for next line: Map<CourseId, Set<StudentId>>
    private final Map<UUID, Set<UUID>> registrationStorage = new LinkedHashMap<>();
    // this format for next line: Map<StudentId, Map<CourseId,List<StepId>>>
    private final Map<UUID, Map<UUID,List<UUID>>> progressStorage = new HashMap<>();
    // will be used for analytics
    private final List<CourseAction> timedActionsStorage = new LinkedList<>();

    // will be deleted in REST API step?
    private AutoCheckService autoCheckService;
    private EducationalMaterialService educationalMaterialService;
    private AccountService accountService;


    @Override
    public void setAutoCheckService(AutoCheckService autoCheckService) {
        this.autoCheckService = autoCheckService;
    }
    @Override
    public void setEducationalMaterialService(EducationalMaterialService educationalMaterialService) {
        this.educationalMaterialService = educationalMaterialService;
    }
    @Override
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public void signUpUser(UUID courseId,
                           UUID studentId){
        Set<UUID> studentsIds = registrationStorage.computeIfAbsent(courseId, k -> new HashSet<>());
        studentsIds.add(studentId);
        System.out.println("Student is signed up now");
    }

    @Override
    public void setStartProgress(UUID courseId,
                                 UUID studentId){
        Map<UUID,List<UUID>> studentProgress = progressStorage.computeIfAbsent(studentId, k->new HashMap<>());
        List<UUID> progressInSteps = new ArrayList<>();
        studentProgress.put(courseId,progressInSteps);
        System.out.println("Progress is initialized");
    }

    @Override
    public void makePassedEducationalStep(UUID courseId,
                                          UUID studentId,
                                          UUID educationalStepId){
        Map<UUID,List<UUID>> studentProgress = progressStorage.get(studentId);
        studentProgress.get(courseId).add(educationalStepId);
        CourseAction courseAction = buildCourseAction(courseId,studentId,ActionType.SEEN,0);
        timedActionsStorage.add(courseAction);
        System.out.println("Educational step is passed");
    }

    @Override
    public void makeProcessedTestStep(UUID courseId,
                                      UUID studentId,
                                      UUID testStepId,
                                      TestAnswer chosenTestAnswer){
        TestStep testStep = educationalMaterialService.getTestStep(testStepId);
        Map<UUID,List<UUID>> studentProgress = progressStorage.get(studentId);
        ActionType receivedActionType = autoCheckService.checkTestTask(testStep,chosenTestAnswer);
        CourseAction courseAction = buildCourseAction(courseId,studentId,receivedActionType,testStep.getScore());
        timedActionsStorage.add(courseAction);
        if(receivedActionType==ActionType.PASSED){
            studentProgress.get(courseId).add(testStepId);
        }
    }

    @Override
    public Step getCurrentStep(UUID courseId,
                               UUID studentId) {
        Map<UUID,List<UUID>> studentProgress = progressStorage.get(studentId);
        List<UUID> studentsCourseSteps = studentProgress.get(courseId);
        Course course = educationalMaterialService.getCourse(courseId);
        if(studentsCourseSteps.size() == course.getSteps().size()){
            Student student = accountService.getStudent(studentId);
            congratulateStudent(student,course);
            return course.getSteps().get(studentsCourseSteps.size()-1);
        }
        return course.getSteps().get(studentsCourseSteps.size()); // next step from last finished
    }

    @Override
    public void congratulateStudent(Student student,
                                    Course course){
        String congratulation = "Congratulations," + student.getName() +
                ",! You've done well. You've already finished course " + course.getTitle();
        System.out.println(congratulation);
    }



    @Override
    public CourseAction buildCourseAction(UUID courseId,
                                          UUID studentId,
                                          ActionType actionType,
                                          Integer score){
        ZonedDateTime actionTime = ZonedDateTime.now();
        switch (actionType){
            case SEEN:
                return new CourseAction(courseId,
                        studentId,
                        actionType,
                        actionTime,
                        0);
            case TRIED:
                return new CourseAction(courseId,
                        studentId,
                        actionType,
                        actionTime,
                        0);
            case PASSED:
                return new CourseAction(courseId,
                        studentId,
                        actionType,
                        actionTime,
                        score);
            default:
                throw new RuntimeException("No such a case for actionType:"+actionType.name());
        }
    }


}
