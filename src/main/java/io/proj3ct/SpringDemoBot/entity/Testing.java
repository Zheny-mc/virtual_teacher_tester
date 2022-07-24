package io.proj3ct.SpringDemoBot.entity;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({ "currentQuestion" })
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class Testing {
    String title;
    List<Question> questionList;
    Integer currentQuestion;

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
}
