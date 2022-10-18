package io.proj3ct.SpringDemoBot.clients.telegram_client;

import io.proj3ct.SpringDemoBot.entity.Testing;
import io.proj3ct.SpringDemoBot.view.StructCourse;

public interface TelegramClient {
    String getStructCourse(String title);
    Testing getTest(String title);
    Boolean getStatusTest();
    void updateStatusTest(Boolean isEndTest);
}
