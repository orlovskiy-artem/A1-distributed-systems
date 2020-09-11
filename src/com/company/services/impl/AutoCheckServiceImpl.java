package com.company.services.impl;

import com.company.models.ActionType;
import com.company.models.TestAnswer;
import com.company.models.TestStep;
import com.company.services.AutoCheckService;

public class AutoCheckServiceImpl implements AutoCheckService {


    // Now Task Steps are only one-answer tests, so the answers will be checked easily
    // but it's possible to make multitest task (like many correct answers), or essay task

    // Checker knows nothing about student
    @Override
    public ActionType checkTestTask(TestStep testStep,
                                    TestAnswer chosenTestAnswer){
        ActionType actionType;
        String chosenAnswerText =  chosenTestAnswer.getAnswerText();
        String correctAnswerText =  testStep.getCorrectAnswer().getAnswerText();
        if(chosenAnswerText.equals(correctAnswerText)){
            actionType = ActionType.PASSED;
            System.out.println("Correct answer, great!");
        }
        else {
            actionType = ActionType.TRIED;
            System.out.println("Wrong answer, try again");
        }
        return actionType;
    }
}
