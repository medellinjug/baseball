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
                + " {\"id\":1,\"code\":\"T-SA\",\"name\":\"TOQUE SACRIFICIO\",\"type\":\"HIT\",\"color\":\"#ffffcc\"}"
                +", {\"id\":2,\"code\":\"T-SO\",\"name\":\"TOQUE SORPRESA\",\"type\":\"HIT\",\"color\":\"#ccffcc\"}"
                +", {\"id\":3,\"code\":\"SO-RB\",\"name\":\"SORPRESA-ROBO\",\"type\":\"HIT\",\"color\":\"#ccffff\"}"
                +", {\"id\":4,\"code\":\"RP-T\",\"name\":\"ROMPE-TOQUE\",\"type\":\"HIT\",\"color\":\"#ccccff\"}"
               +", {\"id\":5,\"code\":\"EM\",\"name\":\"EMPUJAR\",\"type\":\"HIT\",\"color\":\"#ff33ff\"}"



               +", {\"id\":6,\"code\":\"EM-RB\",\"name\":\"EMPUJAR ROBO\",\"type\":\"HIT\",\"color\":\"#ffcccc\"}"


               +", {\"id\":7,\"code\":\"SQ1\",\"name\":\"SQUIZ PLAY 1\",\"type\":\"HIT\",\"color\":\"#ffeecc\"}"
               +", {\"id\":8,\"code\":\"SQ2\",\"name\":\"SQUIZ PLAY 2\",\"type\":\"HIT\",\"color\":\"#ddffcc\"}"


               +", {\"id\":9,\"code\":\"RB\",\"name\":\"ROBO\",\"type\":\"HIT\",\"color\":\"#1aff66\"}"
               +", {\"id\":10,\"code\":\"B-C\",\"name\":\"BATEO-CORRIDO\",\"type\":\"HIT\",\"color\":\"#cce6ff\"}"
               +", {\"id\":11,\"code\":\"C-B\",\"name\":\"CORRER-BATEAR\",\"type\":\"HIT\",\"color\":\"#ff6666\"}"
               +", {\"id\":12,\"code\":\"RP-RB\",\"name\":\"ROMPE-ROBO\",\"type\":\"HIT\",\"color\":\"#ffccee\"}"
               +", {\"id\":13,\"code\":\"RB<\",\"name\":\"ROBO TEMPRANO\",\"type\":\"HIT\",\"color\":\"#f2e6d9\"}"
               +", {\"id\":14,\"code\":\"RB>\",\"name\":\"ROBO TARDE\",\"type\":\"HIT\",\"color\":\"#e0e0eb\"}"
               +", {\"id\":15,\"code\":\"F-T\",\"name\":\"FALSO TOQUE\",\"type\":\"HIT\",\"color\":\"#33adff\"}"
               +", {\"id\":16,\"code\":\"F-T-R-B\",\"name\":\"FALSO TOQUE ROBO\",\"type\":\"HIT\",\"color\":\"#00ffbf\"}"
               +", {\"id\":17,\"code\":\"RB1\",\"name\":\"ROBO 1 VIRADA\",\"type\":\"HIT\",\"color\":\"#ff9900\"}"
               +", {\"id\":18,\"code\":\"RB2\",\"name\":\"ROBO 2 VIRADA\",\"type\":\"HIT\",\"color\":\"#ffff00\"}"






               +", {\"id\":101,\"code\":\"FB4S-I\",\"name\":\"FASTBALL 4 SEAM IN\",\"type\":\"PITCH\",\"color\":\"#ffffcc\"}"
               +", {\"id\":102,\"code\":\"FB4S-O\",\"name\":\"FASTBALL 4 SEAM OUT\",\"type\":\"PITCH\",\"color\":\"#ccffcc\"}"

               +", {\"id\":103,\"code\":\"FB2S-I\",\"name\":\"FASTBALL 2 SEAM IN\",\"type\":\"PITCH\",\"color\":\"#ccffff\"}"
               +", {\"id\":101,\"code\":\"FB2S-O\",\"name\":\"FASTBALL 2 SEAM OUT\",\"type\":\"PITCH\",\"color\":\"#ccccff\"}"

               +", {\"id\":104,\"code\":\"SP\",\"name\":\"SPLITTER\",\"type\":\"PITCH\",\"color\":\"#ffcccc\"}"
               +", {\"id\":105,\"code\":\"CU\",\"name\":\"CUTTER\",\"type\":\"PITCH\",\"color\":\"#cce6ff\"}"
               +", {\"id\":106,\"code\":\"SL\",\"name\":\"SLIDER\",\"type\":\"PITCH\",\"color\":\"#ff6666\"}"
               +", {\"id\":107,\"code\":\"CV-I\",\"name\":\"CURVE IN\",\"type\":\"PITCH\",\"color\":\"#ffccee\"}"
               +", {\"id\":108,\"code\":\"CV-O\",\"name\":\"CURVE OUT\",\"type\":\"PITCH\",\"color\":\"#f2e6d9\"}"
               +", {\"id\":109,\"code\":\"CV-F\",\"name\":\"CURVE FRONTAL\",\"type\":\"PITCH\",\"color\":\"#e0e0eb\"}"
               +", {\"id\":110,\"code\":\"CH-OK\",\"name\":\"CHANGE OK\",\"type\":\"PITCH\",\"color\":\"#33adff\"}"
               +", {\"id\":111,\"code\":\"PM\",\"name\":\"PALMBALL\",\"type\":\"PITCH\",\"color\":\"#00ffbf\"}"
               +", {\"id\":112,\"code\":\"SW\",\"name\":\"SCREW BALL\",\"type\":\"PITCH\",\"color\":\"#ff9900\"}"
               +", {\"id\":113,\"code\":\"FK\",\"name\":\"FORKBALL\",\"type\":\"PITCH\",\"color\":\"#ffff00\"}"
               +", {\"id\":114,\"code\":\"KN\",\"name\":\"KNUCKLEBALL\",\"type\":\"PITCH\",\"color\":\"#ff33ff\"}"
               +", {\"id\":115,\"code\":\"PO\",\"name\":\"PITCH OUT\",\"type\":\"PITCH\",\"color\":\"#1aff66\"}"
               +", {\"id\":114,\"code\":\"PCO\",\"name\":\"PICK OFF\",\"type\":\"PITCH\",\"color\":\"#ffeecc\"}"


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
