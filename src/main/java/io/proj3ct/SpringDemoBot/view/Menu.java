package io.proj3ct.SpringDemoBot.view;

import io.proj3ct.SpringDemoBot.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    public static void setMainMenu(TelegramBot bot) throws TelegramApiException {
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "вывод приветственного сообщения"));
        listofCommands.add(new BotCommand("/test_selection", "выбор теста"));
        listofCommands.add(new BotCommand("/begin_test", "войти в тест"));
        listofCommands.add(new BotCommand("/question", "вывести формулировку вопроса"));
        listofCommands.add(new BotCommand("/answers", "ответить"));
        listofCommands.add(new BotCommand("/next_question", "взять следующий вопрос"));
        listofCommands.add(new BotCommand("/stop", "сбросить результаты прохождения теста"));
        listofCommands.add(new BotCommand("/save_course", "сохранить тест в базу"));
        listofCommands.add(new BotCommand("/delete_course", "удалить курс из базы"));

        bot.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
    }
}
