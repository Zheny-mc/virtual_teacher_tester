package io.proj3ct.SpringDemoBot.action.answerObject;

import io.proj3ct.SpringDemoBot.entity.Question;
import lombok.Value;

@Value
public class NextQuestionAnswer {
    Question currQuestion;
    Boolean isAnswer;
}
