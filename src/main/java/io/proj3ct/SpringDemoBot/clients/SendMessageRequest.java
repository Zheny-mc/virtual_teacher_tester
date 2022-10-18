package io.proj3ct.SpringDemoBot.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;

@Value
public class SendMessageRequest {

    private String title;
    private Boolean isEndTest;

    public SendMessageRequest(String title) {
        this.title = title;
        this.isEndTest = false;
    }

    public SendMessageRequest(Boolean isEndTest) {
        this.title = "test";
        this.isEndTest = isEndTest;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new MessageClientException("Can't create string from object:");
        }
    }
}
