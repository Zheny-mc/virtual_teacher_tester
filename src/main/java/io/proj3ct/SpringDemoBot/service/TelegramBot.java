package io.proj3ct.SpringDemoBot.service;


import io.proj3ct.SpringDemoBot.config.BotConfig;
import io.proj3ct.SpringDemoBot.action.announcementsСommand.States;
import io.proj3ct.SpringDemoBot.action.ActionRun;
import io.proj3ct.SpringDemoBot.action.CollectionAction;
import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.view.Menu;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {

    Map<States, IAction> actionMap;

    final CollectionAction actions;
    final ActionRun actionRun;
    final BotConfig config;

    public TelegramBot(CollectionAction actions,
                       BotConfig config,
                       ActionRun actionRun) {
        this.actions = actions;
        this.config = config;
        this.actionRun = actionRun;

        actionMap = actions.getActions();

        try {
            Menu.setMainMenu(this);
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
            List<String> args = List.of(messageText.split("\\s+"));

            States state = States.fromString(args.get(0));
            actionRun.setState( state );
            actionRun.setAction( actionMap.getOrDefault(state, actionMap.get(States.Start)) );
            actionRun.setName( name );
            actionRun.setArgs( args );

            List<String> response = actionRun.execute();
            response.forEach(i -> sendMessage(chatId, i));
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        message = installKeyBoard(message);

        executeMessage(message);
    }

    private SendMessage installKeyBoard(SendMessage message) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("/start");
        row.add("/stop");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("/answers title_answer1");
        row.add("/answers title_answer2");
        row.add("/answers title_answer3");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("/test_selection 1_1");
        row.add("/begin_test");
        row.add("/question");
        row.add("/next_question");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);
        return message;
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
