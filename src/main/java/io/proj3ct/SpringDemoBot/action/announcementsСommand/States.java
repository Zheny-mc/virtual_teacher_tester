package io.proj3ct.SpringDemoBot.action.announcementsСommand;

public enum States {
    Start("/start"),
    TestSelection("/test_selection"),
    BeginTest("/begin_test"),
    Question("/question"),
    Answer("/answers"),
    NextQuestion("/next_question"),
    Stop("/stop"),
    SaveCourse("/save_course"),
    DeleteCourse("/delete_course");

    private final String title;

    States(String title) {
        this.title = title;
    }

    public String type(){
        return this + "";
    }
    public String getTitle() {
        return title;
    }

    public static States fromString(String value) {
        if (value != null) {
            for (States st : States.values()) {
                if (value.equalsIgnoreCase(st.title)) {
                    return st;
                }
            }
        }
        return States.Start;
    }
}