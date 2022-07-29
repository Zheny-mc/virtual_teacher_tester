package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.action.answerObject.NextQuestionAnswer;
import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NextQuestionAction extends IAction {
    public NextQuestionAction() {
        title = "/next_question";
    }

    @Override
    public Pair<List<String>, NextQuestionAnswer> execute(Testing testing, Question currQuestion, Boolean isAnswer) {
        StringBuilder builder = new StringBuilder();
        if (isAnswer) {
            //-----next Question----
            try {
                currQuestion = testing.next();
                builder.append("следующий вопрос...");
            } catch (Exception e) {
                builder.append(e.getMessage());
            }
        }
        else {
            builder.append("You haven't answered the previous question yet!");
        }
        return new Pair<>(List.of(builder.toString()),
                new NextQuestionAnswer(currQuestion, isAnswer));
    }
}
