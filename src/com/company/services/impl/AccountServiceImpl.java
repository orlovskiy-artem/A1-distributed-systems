package com.company.services.impl;

import com.company.models.Author;
import com.company.models.Student;
import com.company.services.AccountService;

import java.util.*;

public class AccountServiceImpl implements AccountService {
    private final Map<UUID, Student> studentsStorage = new HashMap<>();
    private final Map<UUID, Author> authorsStorage = new HashMap<>();

    @Override
    public UUID signUpStudent(String firstName,
                              String lastName,
                              String description){
        System.out.println("Signing up a student...");
        Student student = new Student(firstName,lastName,description);
        studentsStorage.put(student.getId(),student);
        System.out.println("Signing up a student "+
                student.getName()+
                " is complete");
        return student.getId(); // JWT?
    }

    @Override
    public UUID signUpAuthor(String firstName,
                             String lastName,
                             String description){
        System.out.println("Signing up an author...");
        Author author = new Author(firstName,lastName,description);
        authorsStorage.put(author.getId(),author);
        System.out.println("Signing up an author "+
                author.getName()+
                " is complete");
        return author.getId();
    }

    @Override
    public void deleteStudent(UUID studentId){
        Student student = studentsStorage.remove(studentId);
        System.out.println("Deleting the student "+
                student.getName()+
                " is complete");
    }

    @Override
    public void deleteAuthor(UUID authorId){
        Author author =  authorsStorage.remove(authorId);
        System.out.println("Deleting the author "+
                author.getName()+
                 " is complete");
    }

    @Override
    public UUID updateStudent(UUID studentId,
                              String newFirstName,
                              String newLastName,
                              String newDescription) {
        Student student = studentsStorage.get(studentId);
        System.out.println("Updating the student "+
                student.getName()+
                "...");
        student.setFirstName(newFirstName);
        student.setLastName(newLastName);
        student.setDescription(newDescription);
        System.out.println("The student "+
                student.getName()+
                " is updated");
        return student.getId();
    }

    @Override
    public UUID updateAuthor(UUID authorId,
                             String newFirstName,
                             String newLastName,
                             String newDescription){
        Author author = authorsStorage.get(authorId);
        System.out.println("Updating the author "+
                author.getName()+
                "...");
        author.setFirstName(newFirstName);
        author.setLastName(newLastName);
        author.setDescription(newDescription);
        System.out.println("The author "+
                author.getName()+
                " is updated");
        return author.getId();
    }

    @Override
    public Student getStudent(UUID studentId){
        return studentsStorage.get(studentId);
    }

    @Override
    public Author getAuthor(UUID authorId){
        return authorsStorage.get(authorId);
    }
}
