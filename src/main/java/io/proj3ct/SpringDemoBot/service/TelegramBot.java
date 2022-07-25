package io.proj3ct.SpringDemoBot.service;


import io.proj3ct.SpringDemoBot.config.BotConfig;
import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import io.proj3ct.SpringDemoBot.repositoryes.UniversityСourseRepository;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {


    Question currQuestion;
    Boolean isAnswer;
    Testing testing;

    final UniversityСourse trainingCourse;
    final BotConfig config;

    final StructCourse structCourse;
    final UniversityСourseRepository repository;
    
    
    public TelegramBot(UniversityСourse trainingCourse, BotConfig config, StructCourse structCourse, UniversityСourseRepository repository) {
        this.trainingCourse = trainingCourse;
        this.config = config;
        this.structCourse = structCourse;
        this.repository = repository;
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

        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
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
            List<String> args = List.of(messageText.split(" "));

            switch (args.get(0)) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/test_selection":
                    if (args.size() == 2) {
                        String text = args.get(1);
                        Pattern pattern = Pattern.compile("\\d+\\,\\d+");
                        Matcher matcher = pattern.matcher(text);
                        if (matcher.find()) {
                            var pathFoTest = Arrays.stream(text.split(","))
                                    .map(i -> Integer.parseInt(i))
                                    .collect(Collectors.toList());

                            testing = selectTest(pathFoTest.get(0), pathFoTest.get(1));
                            sendMessage(chatId, testing.toString());
                        }
                    } else {
                        sendMessage(chatId, "Enter the answer!!!");
                    }
                    break;

                case "/begin_test":
                    testing.clearIteratorQuestion();
                    try {
                        currQuestion = testing.getFirst();
                        isAnswer = true;
                        sendMessage(chatId, "ok");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "/question": {
                    String answer = currQuestion.title();
                    for (var i: currQuestion.answerList()) {
                        answer += "\n\t" + i.title();
                    }
                    sendMessage(chatId, answer);
                    isAnswer = false;
                    break;
                }

                case "/answers": {
                    if (args.size() > 1) {
                        StringBuilder answer = new StringBuilder();

                        var answerStudent = currQuestion.answerList().stream()
                                .filter(i-> i.isValid() == true)
                                .collect(Collectors.toList())
                                .get(0).title();

                        if (args.get(1).equals(answerStudent)) {
                            answer.append("Верно!)");
                            isAnswer = true;
                        }
                        else {
                            currQuestion.recommendationList().forEach(i -> answer.append(i.title() + "\n"));
                        }
                        sendMessage(chatId, answer.toString());
                    }
                    else {
                        sendMessage(chatId, "enter the answer!");
                    }
                    break;
                }

                case "/next_question":
                    if (isAnswer) {
                        //-----next Question----
                        try {
                            currQuestion = testing.next();
                        } catch (Exception e) {
                            sendMessage(chatId, e.getMessage());
                        }
                    }
                    else {
                        sendMessage(chatId, "You haven't answered the previous question yet!");
                    }
                    break;

                case "/stop":
                    testing.clearIteratorQuestion();
                    try {
                        currQuestion = testing.getFirst();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                    
                case "/save_course":
                    repository.save(trainingCourse);
                    break;

                case "/delete_course":
                    var сourse = repository.findAll().get(0);
                    repository.delete(сourse);
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized");

            }
        }
    }

    private Testing selectTest(Integer sectionNumber, Integer testNumber) {
        sectionNumber--;
        testNumber--;
        return trainingCourse.сhapterList().get(sectionNumber)
                .testingList().get(testNumber);
    }

    private void startCommandReceived(long chatId, String name){


        String answer = "Hi, " + name +", nice to meet you!\n";
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);

        answer = structCourse.getStructCourse();
        log.info("Replied to user struct cource");
        sendMessage(chatId, answer);

        answer = "выбери тест, который хочешь пройти";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
