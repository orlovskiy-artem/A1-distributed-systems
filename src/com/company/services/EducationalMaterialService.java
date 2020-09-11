package com.company.services;

import com.company.models.*;

import java.net.URI;
import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

public interface EducationalMaterialService {
    UUID createEmptyCourse(String title,
                           String description,
                           Collection<Author> authors,
                           Duration duration,
                           long price);

    Course getCourse(UUID courseId);

    TestStep getTestStep(UUID testStepId);

    void addEducationalStep(UUID courseId,
                            URI educationalMaterialUri);

    void addTestStep(UUID courseId,
                     URI descriptionUri,
                     Collection<TestAnswer> answers,
                     TestAnswer correctAnswer,
                     int score);

    // if it is necessary to edit the course
    void deleteEducationalStep(UUID courseId,
                               EducationalStep educationalStep);

    void deleteTestStep(UUID courseId,
                        TestStep testStep);

    void activateCourse(UUID courseId);

    void deactivateCourse(UUID courseId);

    void deleteCourse(UUID courseId);
}
