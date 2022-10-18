package io.proj3ct.SpringDemoBot.clients.telegram_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.proj3ct.SpringDemoBot.clients.MessageClientException;
import io.proj3ct.SpringDemoBot.clients.SendMessageRequest;
import io.proj3ct.SpringDemoBot.clients.base.HttpClient;
import io.proj3ct.SpringDemoBot.config.MainServerConfig;
import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.entity.for_request.TestingMessageRequest;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramClientImpl implements TelegramClient {

    private final MainServerConfig config;
    private final HttpClient httpClientJdk;

    @Override
    public String getStructCourse(String title) {
        var params = new SendMessageRequest(title).toJson();
        log.info("params:{}", params);

        var response = httpClientJdk.performRequest(
                config.getStructCourse(), params);
        log.info("response:{}", response);

        return response;
    }

    @Override
    public Testing getTest(String title) {
        var params = new SendMessageRequest(title).toJson();
        log.info("params:{}", params);

        var obj = parseTestingMessageRequest(httpClientJdk.performRequest(
                config.getTest(), params) );
        var response = Testing.get(obj);
        log.info("response:{}", response);

        return response;
    }

    @Override
    public Boolean getStatusTest() {
        var response = Boolean.parseBoolean(httpClientJdk.performRequest(
                config.getStatusTest()) );
        log.info("response:{}", response);
        return response;
    }

    @Override
    public void updateStatusTest(Boolean isEndTest) {
        var params = new SendMessageRequest(isEndTest).toJson();
        log.info("params:{}", params);

        httpClientJdk.performRequest(config.getStatusTest(), params);
    }

    private static TestingMessageRequest parseTestingMessageRequest(String jsonObj) {
        try {
            return new ObjectMapper().readValue(jsonObj, TestingMessageRequest.class);
        } catch (JsonProcessingException ex) {
            throw new MessageClientException("Can't parse string:" + jsonObj);
        }
    }

}
