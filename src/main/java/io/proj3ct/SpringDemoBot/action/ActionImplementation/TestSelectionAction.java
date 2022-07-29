package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import io.proj3ct.SpringDemoBot.action.IAction;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestSelectionAction extends IAction {

    Testing testing;
    UniversityСourse trainingCourse;

    public TestSelectionAction() {
        title = "/test_selection";
    }

    @Override
    public Pair<List<String>, Testing> execute(List<String> args, UniversityСourse trainingCourse) {
        this.trainingCourse = trainingCourse;

        List<String> result = new ArrayList<>();
        if (args.size() == 2) {
            String text = args.get(1);
            if (checkTextRegex(text, "\\d+\\,\\d+")) {
                result.add(getTesting(text));
            } else {
                result.add("Неверный ответ!");
            }

        } else {
            result.add("Enter the answer!!!");
        }
        return new Pair<>(result, this.testing);
    }

    private boolean checkTextRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private String getTesting(String text) {
        var pathFoTest = Arrays.stream(text.split(","))
                .map(i -> Integer.parseInt(i))
                .collect(Collectors.toList());

        testing = selectTest(pathFoTest.get(0), pathFoTest.get(1));
        return testing.toString();
    }

    public Testing selectTest(Integer sectionNumber, Integer testNumber) {
        sectionNumber--;
        testNumber--;
        return trainingCourse.сhapterList().get(sectionNumber)
                .testingList().get(testNumber);
    }

}
