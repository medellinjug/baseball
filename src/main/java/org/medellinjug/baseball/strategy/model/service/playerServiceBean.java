package org.medellinjug.baseball.strategy.model.service;

import org.medellinjug.baseball.strategy.model.entity.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ANALIS-SUI-02 on 7/5/17.
 */
public class playerServiceBean {
    private final CopyOnWriteArrayList<Player> eList = PlaysReader.getInstancePlayer();


    public boolean add(Player player) {
        Long next = (this.eList.stream().mapToLong(p -> p.getId()).max().orElse(0L)) + 1;

        Player newPlayer =
                new Player(player.getId(), player.getFullName(), player.getType());

        eList.add(newPlayer);

        return true;
    }
}
