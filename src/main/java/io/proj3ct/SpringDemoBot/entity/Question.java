package io.proj3ct.SpringDemoBot.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    String title;
    List<Answer> answerList;
    List<Recommendation> recommendationList;

    public void add(Answer q) {
        answerList.add(q);
    }

    public void addRecommendation(Recommendation r) {
        recommendationList.add(r);
    }

    public void clear() {
        title = "default";
        answerList.clear();
        recommendationList.clear();
    }
}
