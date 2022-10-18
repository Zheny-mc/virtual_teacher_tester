package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.clients.telegram_client.TelegramClient;
import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.action.IAction;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TestSelectionAction extends IAction {

    private final TelegramClient client;
    public TestSelectionAction(TelegramClient client) {
        this.client = client;
        title = "/test_selection";
    }

    @Override
    public Pair<List<String>, Testing> execute(List<String> args) {
        Testing testing = null;
        List<String> result = new ArrayList<>();
        if (args.size() == 2) {
            String text = args.get(1);
            if (checkTextRegex(text, "\\d+\\_\\d+")) {
                testing = client.getTest(text);
                result.add(testing.toString());
            } else {
                result.add("Неверный ответ!");
            }
        } else {
            result.add("Enter the answer!!!");
        }
        return new Pair<>(result, testing);
    }

    private boolean checkTextRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

}
