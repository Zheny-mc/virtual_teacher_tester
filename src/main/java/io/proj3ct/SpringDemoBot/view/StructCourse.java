package io.proj3ct.SpringDemoBot.view;

import io.proj3ct.SpringDemoBot.entity.UniversityСourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class StructCourse {
    private final UniversityСourse сourse;

    public String getStructCourse() {
        StringBuilder structCourse = new StringBuilder();
        structCourse.append( String.format("Курс: %s\n", сourse.title()) );

        var сhapterList = сourse.сhapterList();
        сhapterList.forEach(сhapter -> {
            int countChapter = сhapterList.indexOf(сhapter) + 1;
            structCourse.append(String.format("%2d) Раздел: %s\n", countChapter, сhapter.title()));
            var testingList = сhapter.testingList();

            int countTest = 1;
            for (var testing: testingList) {
                structCourse.append( String.format("\t\t%2d.%2d) Тест: %s\n",
                        countChapter, countTest, testing.title()) );
                countTest++;
            }
        });


        return structCourse.toString();
    }
}
