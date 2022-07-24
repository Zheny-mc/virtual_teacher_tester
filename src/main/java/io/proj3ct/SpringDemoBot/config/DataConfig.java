package io.proj3ct.SpringDemoBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.proj3ct.SpringDemoBot.entity.*;
import io.proj3ct.SpringDemoBot.view.StructCourse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class DataConfig {

    @Bean
    public UniversityСourse getCourse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Path.of("Test.json");
        String is = Files.readString(path);
        UniversityСourse universityСourse = mapper.readValue(is, UniversityСourse.class);

        return universityСourse;
    }

    @Bean
    public Testing getTesting() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Path.of("Test.json");
        String is = Files.readString(path);
        UniversityСourse universityСourse = mapper.readValue(is, UniversityСourse.class);

        return universityСourse.сhapterList().get(0).testingList().get(0);
    }

}
