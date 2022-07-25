package io.proj3ct.SpringDemoBot.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class UniversityСourse {

    @Id
    private String _id;
    @NonNull
    private String title;
    @NonNull
    private List<Сhapter> сhapterList;

    public void addTest(Сhapter сhapter) {
        сhapterList.add(сhapter);
    }

    public void clearСhapterList() {
        сhapterList.clear();
    }
}
