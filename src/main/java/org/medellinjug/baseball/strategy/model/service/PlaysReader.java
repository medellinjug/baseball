package org.medellinjug.baseball.strategy.model.service;


import org.medellinjug.baseball.strategy.model.entity.Play;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Hilmer on 25/06/17.
 * MedellinJUG.org
 */
public class PlaysReader {
    private static final CopyOnWriteArrayList<Play> eList = new CopyOnWriteArrayList<>();

    static {
       String jsonString = "["
                + " {\"id\":1,\"code\":\"RRF\",\"name\":\"Rolling right field\",\"type\":\"HIT\",\"color\":\"#cce6ff\"}"
                +", {\"id\":2,\"code\":\"RCF\",\"name\":\"Rolling center field\",\"type\":\"HIT\",\"color\":\"#ccffe6\"}"
                +", {\"id\":3,\"code\":\"RCF\",\"name\":\"Rolling left field\",\"type\":\"HIT\",\"color\":\"#ffffcc\"}"

                +", {\"id\":4,\"code\":\"FRF\",\"name\":\"Fly right field\",\"type\":\"HIT\",\"color\":\"#ffb3cc\"}"
                +", {\"id\":5,\"code\":\"FCF\",\"name\":\"Fly center field\",\"type\":\"HIT\",\"color\":\"#ccffff\"}"


                +"]";

        try {

            ObjectMapper mapper = new ObjectMapper();

            Play[] myPlayss = mapper.readValue(jsonString, Play[].class);

            eList.addAll(Arrays.asList(myPlayss));
            //eList.addAll(Arrays.asList(myPlayss).stream().sorted(Comparator.comparing(Play::getType).thenComparing(Play::getName)).collect(Collectors.toList()));

        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

    }

    private PlaysReader(){}

    public static CopyOnWriteArrayList<Play> getInstance(){
        return eList;
    }
}
