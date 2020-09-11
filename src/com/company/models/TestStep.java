package com.company.models;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;


/**
 * TestStep
 *
 */
public class TestStep implements Step {
    private final UUID id;
    private final UUID courseId;
    private final URI descriptionUri;
    private final Collection<TestAnswer> answers;
    private final TestAnswer correctAnswer;
    private final int score;

    public TestStep(UUID courseId,
                    URI descriptionUri,
                    Collection<TestAnswer> answers,
                    TestAnswer correctAnswer,
                    int score) {
        this.id =  UUID.randomUUID();
        this.courseId = courseId;
        this.descriptionUri = descriptionUri;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.score = score;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public URI getDescriptionUri() {
        return descriptionUri;
    }

    public Collection<TestAnswer> getAnswers() {
        return answers;
    }

    public TestAnswer getCorrectAnswer() {
        return correctAnswer;
    }

    public int getScore() {
        return score;
    }

}
