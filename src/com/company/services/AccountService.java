package com.company.services;

import com.company.models.Author;
import com.company.models.Student;

import java.util.UUID;

public interface AccountService {
    UUID signUpStudent(String firstName,
                       String lastName,
                       String description);

    UUID signUpAuthor(String firstName,
                      String lastName,
                      String description);

    void deleteStudent(UUID studentId);

    void deleteAuthor(UUID authorId);

    // update if needed for future
    UUID updateStudent(UUID studentId,
                       String newFirstName,
                       String newLastName,
                       String newDescription);

    UUID updateAuthor(UUID authorId,
                      String newFirstName,
                      String newLastName,
                      String newDescription);

    Student getStudent(UUID studentId);

    Author getAuthor(UUID authorId);
}
