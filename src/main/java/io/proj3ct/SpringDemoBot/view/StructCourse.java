package io.proj3ct.SpringDemoBot.view;

import io.proj3ct.SpringDemoBot.clients.telegram_client.TelegramClient;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class StructCourse {
    private final TelegramClient client;
    public String getStructCourse() {
        return client.getStructCourse("title_course1");
    }
}
