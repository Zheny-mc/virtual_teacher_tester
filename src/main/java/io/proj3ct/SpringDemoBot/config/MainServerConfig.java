package io.proj3ct.SpringDemoBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MainServerConfig {

    @Value("${main_server.struct_course}")
    String structCourse;

    @Value("${main_server.test}")
    String test;

    @Value("${main_server.status_test}")
    String statusTest;
}
