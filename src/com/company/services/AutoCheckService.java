package com.company.services;

import com.company.models.ActionType;
import com.company.models.TestAnswer;
import com.company.models.TestStep;

public interface AutoCheckService {
    ActionType checkTestTask(TestStep testStep,
                             TestAnswer chosenTestAnswer);
}
