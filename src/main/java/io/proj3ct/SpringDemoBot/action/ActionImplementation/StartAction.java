package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("StartAction")
@Slf4j
public class StartAction extends IAction {

    public StartAction() {
        title = "/start";
    }

    @Override
    public List<String> execute(String name, StructCourse structCourse) {
        log.info("Replied to user", name);

        return List.of(
                String.format("Привет %s, приятно познакомиться!\n", name),
                structCourse.getStructCourse(),
                "выбери тест, который хочешь пройти\n"
        );
    }

}
