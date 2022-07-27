package io.proj3ct.SpringDemoBot.service;


import io.proj3ct.SpringDemoBot.config.BotConfig;
import io.proj3ct.SpringDemoBot.entity.Question;
import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import io.proj3ct.SpringDemoBot.repositoryes.UniversityСourseRepository;
import io.proj3ct.SpringDemoBot.service.command.States;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.proj3ct.SpringDemoBot.service.command.States.*;
import static io.proj3ct.SpringDemoBot.view.Menu.setMainMenu;


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

        try {
            setMainMenu(this);
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
            List<String> args = List.of(messageText.split("\\s+"));
            List<String> response = new ArrayList<>();

            States state = States.fromString(args.get(0));
            switch (state) {
                case Start:
                    response = startCommandReceived(update.getMessage().getChat().getFirstName());
                    break;

                case TestSelection:
                    handlerTestSelection(args, response);
                    break;

                case BeginTest:
                    response.add( clearProgressTest() );
                    break;

                case Question: {
                    response.add( gettingWordingQuestion() );
                    break;
                }

                case Answer: {
                    handlerAnswer(args, response);
                    break;
                }

                case NextQuestion:
                    response.add( nextQuestion() );
                    break;

                case Stop:
                    response.add( stopTest() );
                    break;
                    
                case SaveCourse:
                    response.add( saveСourse() );
                    break;

                case DeleteCourse:
                    response.add( removeCourse() );
                    break;

                default:
                    response.add( getDefaultAnswer() );
            }

            response.forEach(i -> sendMessage(chatId, i));
        }
    }

    private void handlerTestSelection(List<String> args, List<String> response) {
        if (args.size() == 2) {
            String text = args.get(1);
            if (checkTextRegex(text, "\\d+\\,\\d+")) {
                response.add(getTesting(text));
            }
        } else {
            response.add("Enter the answer!!!");
        }
    }

    private void handlerAnswer(List<String> args, List<String> response) {
        if (args.size() > 1) {
            response.add( checkAnswerStudent(args.get(1)) );
        }
        else {
            response.add( "enter the answer!" );
        }
    }

    public String getDefaultAnswer() {
        return "Sorry, command was not recognized";
    }

    public String removeCourse() {
        var сourse = repository.findAll().get(0);
        repository.delete(сourse);
        return "Курс удален!";
    }

    public String saveСourse() {
        repository.save(trainingCourse);
        return "Курс сохранен!";
    }

    public String stopTest() {
        log.info("Прохождение теста окончено.");
        String answer;
        testing.clearIteratorQuestion();
        try {
            currQuestion = testing.getFirst();
            answer = "Вы вышли из прохождения теста.";
        } catch (Exception e) {
            answer = e.getMessage();
            log.error(e.getMessage());
        }
        return answer;
    }

    public String nextQuestion() {
        StringBuilder builder = new StringBuilder();
        if (isAnswer) {
            //-----next Question----
            try {
                currQuestion = testing.next();
            } catch (Exception e) {
                builder.append(e.getMessage());
            }
        }
        else {
            builder.append("You haven't answered the previous question yet!");
        }
        return builder.toString();
    }

    public String checkAnswerStudent(String answerStudent) {
        StringBuilder answer = new StringBuilder();

        var trueAnswer = currQuestion.answerList().stream()
                .filter(i-> i.isValid() == true)
                .collect(Collectors.toList())
                .get(0).title();

        if (answerStudent.equals(trueAnswer)) {
            answer.append("Верно!)");
            isAnswer = true;
        }
        else {
            currQuestion.recommendationList().forEach(i -> answer.append(i.title() + "\n"));
        }
        return answer.toString();
    }

    public String gettingWordingQuestion() {
        StringBuilder answer = new StringBuilder(currQuestion.title());
        currQuestion.answerList().forEach( i -> answer.append("\n\t" + i.title()) );
        isAnswer = false;
        return answer.toString();
    }

    public String clearProgressTest() {
        try {
            testing.clearIteratorQuestion();
            currQuestion = testing.getFirst();
            isAnswer = true;
            return "ok";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkTextRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public String getTesting(String text) {
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

    public List<String> startCommandReceived(String name){
        log.info("Replied to user " + name);
        List<String> answer = List.of(
                String.format("Hi %s, nice to meet you!\n", name),
                structCourse.getStructCourse(),
                "выбери тест, который хочешь пройти\n"
        );
        return answer;
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
