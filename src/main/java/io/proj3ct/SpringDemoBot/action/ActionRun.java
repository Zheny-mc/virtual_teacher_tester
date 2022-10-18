package io.proj3ct.SpringDemoBot.action;

import io.proj3ct.SpringDemoBot.clients.telegram_client.TelegramClient;
import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import io.proj3ct.SpringDemoBot.action.announcementsСommand.States;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@RequiredArgsConstructor
public class ActionRun {

    States state;
    IAction action;
    String name;
    List<String> args;
    List<String> response;

    Question currQuestion;
    Boolean isAnswer;
    Testing testing;
    final StructCourse structCourse;
    final TelegramClient telegramClient;
    public List<String> execute() {
        switch (state) {
            case Start:
                response = action.execute( name, structCourse );
                telegramClient.updateStatusTest(Boolean.FALSE);
                break;
            case TestSelection: {
                var result = action.execute(args);
                response = result.getKey();
                testing = result.getValue();
                break;
            }
            case BeginTest: {
                var result = action.execute(testing, currQuestion);
                response = result.getKey();
                testing = result.getValue().getTesting();
                currQuestion = result.getValue().getCurrQuestion();
                isAnswer = result.getValue().getIsAnswer();
                break;
            }
            case Question: {
                var result = action.execute(currQuestion, isAnswer);
                response = result.getKey();
                isAnswer = result.getValue();
                break;
            }
            case Answer: {
                var result = action.execute(args, currQuestion);
                response = result.getKey();
                isAnswer = result.getValue();
                break;
            }
            case NextQuestion: {
                var result = action.execute(testing, currQuestion, isAnswer);
                response = result.getKey();
                currQuestion = result.getValue().getCurrQuestion();
                isAnswer = result.getValue().getIsAnswer();
                break;
            }
            case Stop: {
                var result = action.execute(testing);
                response = result.getKey();
                isAnswer = result.getValue();
                telegramClient.updateStatusTest(Boolean.TRUE);
                break;
            }
            default:
                response.add( getDefaultAnswer() );
        }
        return response;
    }

    private String getDefaultAnswer() {
        return "Sorry, command was not recognized";
    }

}
