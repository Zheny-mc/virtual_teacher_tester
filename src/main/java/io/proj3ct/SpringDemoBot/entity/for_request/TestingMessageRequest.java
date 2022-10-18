package io.proj3ct.SpringDemoBot.entity.for_request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.proj3ct.SpringDemoBot.entity.Question;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.checkerframework.framework.qual.NoQualifierParameter;

import java.util.List;

@Data
public class TestingMessageRequest {
    private String title;
    private List<Question> questionList;
}

