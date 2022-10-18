package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.entity.Question;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerAction extends IAction {

    Question currQuestion;
    Boolean isAnswer;

    public AnswerAction() {
        title = "/answers";
    }

    @Override
    public Pair<List<String>, Boolean> execute(List<String> args, Question currQuestion) {
        this.currQuestion = currQuestion;
        List<String> result = new ArrayList<>();

        if (args.size() > 1) {
            result.add( checkAnswerStudent(args.get(1)) );
        }
        else {
            result.add("Enter the answer!");
        }
        return new Pair<>(result, isAnswer);
    }

    private String checkAnswerStudent(String answerStudent) {
        StringBuilder answer = new StringBuilder();

        var trueAnswer = currQuestion.getAnswerList().stream()
                .filter(i-> i.getIsValid() == true)
                .collect(Collectors.toList())
                .get(0).getTitle();

        if (answerStudent.equals(trueAnswer)) {
            answer.append("Верно!)");
            isAnswer = true;
        }
        else {
            currQuestion.getRecommendationList().forEach(i -> answer.append(i.getTitle() + "\n"));
        }
        return answer.toString();
    }
}
