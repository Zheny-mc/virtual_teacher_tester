package io.proj3ct.SpringDemoBot.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Сhapter {
    String title;
    List<Testing> testingList;

    public void addTest(Testing testing) {
        testingList.add(testing);
    }

    public void clearTestingList() {
        testingList.clear();
    }
}
