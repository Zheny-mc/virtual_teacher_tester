package io.proj3ct.SpringDemoBot.clients.base;

public interface HttpClient {

    String performRequest(String url, String params);

    String performRequest(String url);
}
