package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.action.answerObject.BeginTestAnswer;
import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BeginTestAction extends IAction {
    Question currQuestion;
    Boolean isAnswer;

    public BeginTestAction() {
        title = "/begin_test";
    }

    @Override
    public Pair<List<String>, BeginTestAnswer> execute(Testing testing, Question question) {

        List<String> result = new ArrayList<>();
        try {
            testing.clearIteratorQuestion();
            currQuestion = testing.getFirst();
            isAnswer = true;
            result.add("Прохождение теста началось!");
        } catch (Exception e) {
            result.add(e.getMessage());
            throw new RuntimeException(e);
        }
        return new Pair<>(result, new BeginTestAnswer(testing, currQuestion, isAnswer));
    }
}
