package io.proj3ct.SpringDemoBot.action;

import io.proj3ct.SpringDemoBot.action.announcementsСommand.States;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@ToString
public class CollectionAction {

    @Autowired
    private List<IAction> actions;

    public CollectionAction() {
        this.actions = new ArrayList<>();
    }

    public Map<States, IAction> getActions() {
        Map<States, IAction> map = new TreeMap<>();
        actions.forEach( i -> map.put( States.fromString(i.title), i) );
        return map;
    }
}

//        map.forEach((k, v) -> System.out.println(k + " => " + v));