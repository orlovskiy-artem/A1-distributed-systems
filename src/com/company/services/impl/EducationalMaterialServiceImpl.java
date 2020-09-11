package com.company.services.impl;

import com.company.models.*;
import com.company.services.EducationalMaterialService;

import java.net.URI;
import java.time.Duration;
import java.util.*;

public class EducationalMaterialServiceImpl implements EducationalMaterialService {
    private final Map<UUID, Course> coursesStorage = new HashMap<>();
    private final Map<UUID, EducationalStep> educationalStepStorage = new HashMap<>();
    private final Map<UUID, TestStep> testStepStorage = new HashMap<>();

    @Override
    public UUID createEmptyCourse(String title,
                                  String description,
                                  Collection<Author> authors,
                                  Duration duration,
                                  long price){
        ArrayList<Step> steps = new ArrayList<>();
        Course newCourse = new Course(title,
                description,
                authors,
                duration,
                CourseStatus.IN_DEVELOPMENT,
                steps,
                price);
        coursesStorage.put(newCourse.getId(), newCourse);
        return newCourse.getId();
    }

    @Override
    public Course getCourse(UUID courseId) {
        return coursesStorage.get(courseId);
    }
    @Override
    public TestStep getTestStep(UUID testStepId) {
        return testStepStorage.get(testStepId);
    }


    @Override
    public void addEducationalStep(UUID courseId,
                                   URI educationalMaterialUri){
        Course course = coursesStorage.get(courseId);
        EducationalStep educationalStep = new EducationalStep(courseId,
                educationalMaterialUri);
        course.getSteps().add(educationalStep);
        educationalStepStorage.put(educationalStep.getId(),educationalStep);
        System.out.println("New educational step was added");
    }

    @Override
    public void addTestStep(UUID courseId,
                            URI descriptionUri,
                            Collection<TestAnswer> answers,
                            TestAnswer correctAnswer,
                            int score){
        Course course = coursesStorage.get(courseId);
        TestStep testStep = new TestStep(course.getId(),
                descriptionUri,
                answers,
                correctAnswer,
                score);
        course.getSteps().add(testStep);
        testStepStorage.put(testStep.getId(),testStep);
        System.out.println("New test step was added");
    }


    @Override
    public void deleteEducationalStep(UUID courseId,
                                      EducationalStep educationalStep){
        Course course = coursesStorage.get(courseId);
        course.getSteps().remove(educationalStep);
        educationalStepStorage.remove(educationalStep.getId());
        System.out.println("Educational step step was deleted");
    }
    @Override
    public void deleteTestStep(UUID courseId,
                               TestStep testStep){
        Course course = coursesStorage.get(courseId);
        course.getSteps().remove(testStep);
        testStepStorage.remove(testStep.getId());
        System.out.println("Test step was deleted");
    }

    @Override
    public void activateCourse(UUID courseId){
        Course course = coursesStorage.get(courseId);
        course.setStatus(CourseStatus.ACTIVE);
        System.out.println(course.getTitle() + " course is activated");
    }

    @Override
    public void deactivateCourse(UUID courseId){
        Course course = coursesStorage.get(courseId);
        course.setStatus(CourseStatus.DEPRECATED);
        System.out.println(course.getTitle() + " course is deprecated");
    }

    @Override
    public void deleteCourse(UUID courseId){
        Course course = coursesStorage.remove(courseId);
        System.out.println(course.getTitle() + " course was deleted");
    }
}
