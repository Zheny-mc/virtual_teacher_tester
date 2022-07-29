package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.entity.Testing;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopAction extends IAction {

    public StopAction() {
        title = "/stop";
    }

    @Override
    public Pair<List<String>, Boolean> execute(Testing testing) {
        String answer = "Прохождение теста окончено.";
        Boolean isAnswer = true;
        testing.clearIteratorQuestion();
        log.info(answer);
        return new Pair<>(List.of(answer), isAnswer);
    }
}
