package io.proj3ct.SpringDemoBot.clients.base;

public class HttpClientException extends RuntimeException {
    public HttpClientException(String msg) {
        super(msg);
    }
}
