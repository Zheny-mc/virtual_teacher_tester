package io.proj3ct.SpringDemoBot.action.ActionImplementation;

import io.proj3ct.SpringDemoBot.action.IAction;
import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import io.proj3ct.SpringDemoBot.repositoryes.UniversityСourseRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteCourseAction extends IAction {
    public DeleteCourseAction() {
        title = "/delete_course";
    }

    @Override
    public List<String> execute(UniversityСourseRepository repository, UniversityСourse trainingCourse) {
        repository.delete(trainingCourse);
        return List.of("Курс удален!");
    }
}