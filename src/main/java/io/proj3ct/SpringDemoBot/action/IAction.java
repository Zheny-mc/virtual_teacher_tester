package io.proj3ct.SpringDemoBot.action;

import io.proj3ct.SpringDemoBot.action.answerObject.BeginTestAnswer;
import io.proj3ct.SpringDemoBot.action.answerObject.NextQuestionAnswer;
import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import io.proj3ct.SpringDemoBot.repositoryes.UniversityСourseRepository;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import javafx.util.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public abstract class IAction {
    protected String title;

    public List<String> execute(String name, StructCourse structCourse) {
        return null;
    }

    public Pair<List<String>, Testing> execute(List<String> args, UniversityСourse trainingCourse) {
        return null;
    }

    public Pair<List<String>, BeginTestAnswer> execute(Testing testing, Question question) {
        return null;
    }
    public Pair<List<String>, Boolean> execute(Question currQuestion, Boolean isAnswer) {
        return null;
    }

    public Pair<List<String>, Boolean> execute(List<String> args, Question currQuestion) {
        return null;
    }

    public Pair<List<String>, NextQuestionAnswer> execute(Testing testing, Question currQuestion, Boolean isAnswer) {
        return null;
    }

    public Pair<List<String>, Boolean> execute(Testing testing) {
        return null;
    }

    public List<String> execute(UniversityСourseRepository repository, UniversityСourse trainingCourse) {
        return null;
    }


}
