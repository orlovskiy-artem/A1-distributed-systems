package com.company;

import com.company.services.*;
import com.company.services.impl.*;
import com.company.models.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // This program is an attempt to copy architecture from MOOC platform Coursera

        // As a REST API replacement, connection between services by set method.
        // initialize services
        AutoCheckService autoCheckService = new AutoCheckServiceImpl();
        CourseProgressService courseProgressService = new CourseProgressServiceImpl();
        EducationalMaterialService educationalMaterialService =
                new EducationalMaterialServiceImpl();
        PaymentService paymentService = new PaymentServiceImpl();
        AccountService accountService = new AccountServiceImpl();

        // services linking
        courseProgressService.setAutoCheckService(autoCheckService);
        courseProgressService.setEducationalMaterialService(educationalMaterialService);
        courseProgressService.setAccountService(accountService);

        ArrayList<UUID> studentsIds = createStudents(accountService);
        ArrayList<UUID> authorsIds = createAuthors(accountService);

        updateAuthorAccount(accountService,authorsIds.get(0));

        // Create an empty course
        Author authorExample = accountService.getAuthor(authorsIds.get(0));
        UUID nlpCourseId = createEmptyCourse(educationalMaterialService,authorExample);

        // Filling it with steps
        addEducationalStep1(educationalMaterialService,nlpCourseId);
        addTestStep1(educationalMaterialService,nlpCourseId);
        addEducationStep2(educationalMaterialService,nlpCourseId);
        addTestStep2(educationalMaterialService,nlpCourseId);

        // Activate course
        educationalMaterialService.activateCourse(nlpCourseId);

        System.out.println("Info about course:");
        System.out.println(educationalMaterialService.getCourse(nlpCourseId));

        // Signing up a student in course and paying for it
        UUID thomasAndersonId =  studentsIds.get(1);
        courseProgressService.signUpUser(nlpCourseId,thomasAndersonId);
        Course nlpCourse = educationalMaterialService.getCourse(nlpCourseId);
        Student thomasAnderson = accountService.getStudent(thomasAndersonId);
        paymentService.payForCourse(thomasAnderson,nlpCourse);

        // Make progress
        processCourse(courseProgressService,nlpCourseId,thomasAndersonId);

        // Delete a course
        educationalMaterialService.deactivateCourse(nlpCourseId);
        educationalMaterialService.deleteCourse(nlpCourseId);

        // Delete authors
        for (UUID authorId: authorsIds) {
            accountService.deleteAuthor(authorId);
        }

        for (UUID studentId: studentsIds) {
            accountService.deleteStudent(studentId);
        }
    }


    public static ArrayList<UUID> createStudents(AccountService accountService){
        // Students
        UUID jasonSmithId =  accountService.signUpStudent("Jason",
                "Smith",
                "Simple description");
        UUID thomasAndersonId = accountService.signUpStudent("Thomas",
                "Anderson",
                "Description");
        UUID charlesTurnerId = accountService.signUpStudent("Charles",
                "Turner",
                "Difficult description");
        // Only for example
        ArrayList<UUID> studentsIds = new ArrayList<>();
        studentsIds.add(jasonSmithId);
        studentsIds.add(thomasAndersonId);
        studentsIds.add(charlesTurnerId);

        System.out.println("Students");
        for (UUID studentId:studentsIds) {
            Student student = accountService.getStudent(studentId);
            System.out.println("Student's info:\n" +student);
        }
        return studentsIds;
    }

    public static ArrayList<UUID> createAuthors(AccountService accountService){
        // Authors
        UUID paulJacksonId = accountService.signUpAuthor("Paul",
                "Jackson",
                "lorem ipsum dolor");
        Author author = accountService.getAuthor(paulJacksonId);
        System.out.println("Author's info:\n"+author);
        ArrayList<UUID> authorsIds = new ArrayList<>();
        authorsIds.add(paulJacksonId);
        return authorsIds;
    }

    public static void updateAuthorAccount(AccountService accountService,
                                           UUID paulJacksonId){
        // Example of update an account
        accountService.updateAuthor(paulJacksonId,
                "Paulo",
                "Jackson",
                "New lorem ipsum dolor");
        Author author = accountService.getAuthor(paulJacksonId);
        System.out.println("Author's info:\n"+author);
    }

    public static UUID createEmptyCourse(EducationalMaterialService educationalMaterialService,
                                         Author author){
        // returns UUID of course
        Collection<Author> authors = new ArrayList<>();
        authors.add(author);
        UUID nlpCourseId = educationalMaterialService.createEmptyCourse("Natural Language Processing",
                "An introduction to NLP",
                authors, Duration.ofHours(87),80);
        return nlpCourseId;
    }

    //Example of filling with educational steps
    public static void addEducationalStep1(EducationalMaterialService educationalMaterialService,
                                           UUID nlpCourseId){
        String educationalStep1Link =  "https://www.youtube.com/watch?v=8rXD5-xhemo&list=PLoROMvodv4rOhcuXMZkNm7j3fVwBBY42z&index=1&ab_channel=stanfordonline";
        URI educationalStep1Uri = null;
        try {
            educationalStep1Uri = new URI(educationalStep1Link);
            educationalMaterialService.addEducationalStep(nlpCourseId,educationalStep1Uri);
        } catch (URISyntaxException e) {
            System.out.println("Link is not working");
            // We expect correct work of the link now
        }
    }

    //Example of filling with test steps
    public static void addTestStep1(EducationalMaterialService educationalMaterialService,
                                    UUID nlpCourseId){
        String testStep1DescriptionLink = "https://www.analyticsvidhya.com/blog/2017/07/30"+
                "-questions-test-data-scientist-natural-language-processing-solution-skilltest-nlp/";
        URI testStep1DescriptionUri = null;
        TestAnswer test1A = new TestAnswer("A) 1 and 2");
        TestAnswer test1B = new TestAnswer("B) 2 and 4");
        TestAnswer test1C = new TestAnswer("C) 1 and 3");
        TestAnswer test1D = new TestAnswer("D) 1, 2 and 3");
        TestAnswer test1E = new TestAnswer("E) 2, 3 and 4");
        TestAnswer test1F = new TestAnswer("F) 1, 2, 3 and 4");
        Collection<TestAnswer> test1Answers = Arrays.asList(test1A,test1B,test1C,test1D,test1E,test1F);
        try {
            testStep1DescriptionUri = new URI(testStep1DescriptionLink);
            educationalMaterialService.addTestStep(nlpCourseId,
                    testStep1DescriptionUri,test1Answers,test1C,1);
        } catch (URISyntaxException e) {
            System.out.println("Link is not working");
            // We expect correct work of the link now
        }
    }

    public static void addEducationStep2(EducationalMaterialService educationalMaterialService,
                                         UUID nlpCourseId){
        String educationalStep2Link =  "https://www.youtube.com/watch?v=kEMJRjEdNzM&list=PLoROMvodv4rOhcuXMZkNm7j3fVwBBY42z&index=2&ab_channel=stanfordonline";
        URI educationalStep2Uri = null;
        try {
            educationalStep2Uri = new URI(educationalStep2Link);
            educationalMaterialService.addEducationalStep(nlpCourseId,educationalStep2Uri);
        } catch (URISyntaxException e) {
            System.out.println("Link is not working");
            // We expect correct work of the link now
        }
    }

    public static void addTestStep2(EducationalMaterialService educationalMaterialService,
                                    UUID nlpCourseId){
        String testStep2DescriptionLink = "https://www.analyticsvidhya.com/blog/2017/07/30-questions-test-data-scientist-natural-language-processing-solution-skilltest-nlp/";
        URI testStep2DescriptionUri = null;
        TestAnswer test2A = new TestAnswer("A) 7");
        TestAnswer test2B = new TestAnswer("B) 8");
        TestAnswer test2C = new TestAnswer("C) 9");
        TestAnswer test2D = new TestAnswer("D) 10");
        TestAnswer test2E = new TestAnswer("E) 11");
        Collection<TestAnswer> test2Answers = Arrays.asList(test2A,test2B,test2C,test2D,test2E);
        try {
            testStep2DescriptionUri = new URI(testStep2DescriptionLink);
            educationalMaterialService.addTestStep(nlpCourseId,
                    testStep2DescriptionUri,test2Answers,test2C,1);
        } catch (URISyntaxException e) {
            System.out.println("Link is not working");
            // We expect correct work of the link now
        }
    }

    public static void processCourse(CourseProgressService courseProgressService,
                                     UUID nlpCourseId,
                                     UUID thomasAndersonId){
        courseProgressService.setStartProgress(nlpCourseId,thomasAndersonId);
        // Educational step 1
        Step currentStep = courseProgressService.getCurrentStep(nlpCourseId,thomasAndersonId);
        courseProgressService.makePassedEducationalStep(nlpCourseId,thomasAndersonId,currentStep.getId());
        // Test step 1
        currentStep = courseProgressService.getCurrentStep(nlpCourseId,thomasAndersonId);
        System.out.println("First test attempt:");
        TestAnswer test1A = new TestAnswer("A) 1 and 2");
        courseProgressService.makeProcessedTestStep(nlpCourseId,thomasAndersonId,currentStep.getId(),test1A);
        System.out.println("Second test attempt:");
        TestAnswer test1C = new TestAnswer("C) 1 and 3");
        courseProgressService.makeProcessedTestStep(nlpCourseId,thomasAndersonId,currentStep.getId(),test1C);
        // Educational step 2
        currentStep = courseProgressService.getCurrentStep(nlpCourseId,thomasAndersonId);
        courseProgressService.makePassedEducationalStep(nlpCourseId,thomasAndersonId,currentStep.getId());
        // Test step 2
        currentStep = courseProgressService.getCurrentStep(nlpCourseId,thomasAndersonId);
        System.out.println("First test attempt:");
        TestAnswer test2C = new TestAnswer("C) 9");
        courseProgressService.makeProcessedTestStep(nlpCourseId,thomasAndersonId,currentStep.getId(),test2C);
    }
}
