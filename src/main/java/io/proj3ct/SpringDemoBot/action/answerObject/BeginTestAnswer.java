package io.proj3ct.SpringDemoBot.action.answerObject;

import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import lombok.Value;

@Value
public class BeginTestAnswer {
    Testing testing;
    Question currQuestion;
    Boolean isAnswer;
}
