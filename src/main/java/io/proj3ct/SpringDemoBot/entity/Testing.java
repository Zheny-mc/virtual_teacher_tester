package io.proj3ct.SpringDemoBot.entity;


import io.proj3ct.SpringDemoBot.entity.for_request.TestingMessageRequest;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class Testing {
    String title;
    List<Question> questionList;
    Integer currentQuestion;

    public Testing(String title, List<Question> questionList) {
        this.title = title;
        this.questionList = questionList;
        this.currentQuestion = 0;
    }

    public Testing(String title) {
        this.title = title;
        this.questionList = new ArrayList<>();
        this.currentQuestion = 0;
    }

    public void clearIteratorQuestion() {
        currentQuestion = 0;
    }

    public void add(Question q) {
        questionList.add(q);
    }

    public Question getFirst() throws Exception {
        if (questionList.isEmpty())
            throw new Exception("There are no questions");

        currentQuestion = 0;
        return questionList.get(currentQuestion);
    }

    public Question next() throws Exception {
        if (currentQuestion + 1 >= questionList.size()) {
            throw new Exception("No more questions");
        }
        ++currentQuestion;
        return questionList.get(currentQuestion);
    }

    public static Testing get(TestingMessageRequest obj) {
        return new Testing(
                obj.getTitle(),
                obj.getQuestionList()
        );
    }
}
