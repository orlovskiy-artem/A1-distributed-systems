package com.company.models;

import java.net.URI;
import java.util.UUID;

public class EducationalStep implements Step {
    private final UUID id;
    private final UUID courseId;
    private final URI eduMaterialUri;

    public EducationalStep(UUID courseId,
                           URI eduMaterialUri) {
        this.id = UUID.randomUUID();
        this.courseId = courseId;
        this.eduMaterialUri = eduMaterialUri;
    }

    public UUID getId() {
        return id;
    }

    // can be necessary to view course if ,for example, open link with step id will be shared to non-course member
    public UUID getCourseId() {
        return courseId;
    }

    // getter for educational material of this step
    public URI getEduMaterialUri() {
        return eduMaterialUri;
    }

}
