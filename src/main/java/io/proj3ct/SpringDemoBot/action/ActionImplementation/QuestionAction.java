package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.entity.Question;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionAction extends IAction {

    public QuestionAction() {
        title = "/question";
    }

    @Override
    public Pair<List<String>, Boolean> execute(Question currQuestion, Boolean isAnswer) {
        StringBuilder answer = new StringBuilder(currQuestion.getTitle());
        currQuestion.getAnswerList().forEach( i -> answer.append("\n\t" + i.getTitle()) );
        isAnswer = false;
        return new Pair<>( List.of( answer.toString() ), isAnswer);
    }
}
