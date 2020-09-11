package com.company.models;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Course {
    private final UUID id;
    private final String title;
    private String description;
    private Collection<Author> authors;
    private Duration duration;
    private CourseStatus status;
    private List<Step> steps;
    private final long price;


    public Course(String title,
                  String description,
                  Collection<Author> authors,
                  Duration duration,
                  CourseStatus status,
                  List<Step> steps,
                  long price
                  ){
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.duration = duration;
        this.status = status;
        this.steps = steps;
        this.price = price;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) throws IllegalArgumentException {
        if(authors.isEmpty()){
            throw new IllegalArgumentException("Number of authors must be more than 1!");
        }
        this.authors = authors;
    }


    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) throws Exception {
        if(steps.isEmpty()){
            throw new Exception("Number of steps must be more than 1!");
        }
        this.steps = steps;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException{
        if(description.isBlank()){
            throw new IllegalArgumentException("Description must contain some information.");
        }
        this.description = description;
    }

    public long getPrice(){ return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "platformId=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                ", duration=" + duration +
                ", status=" + status +
                ", steps=" + steps +
                ", price=" + price +
                '}';
    }
}
