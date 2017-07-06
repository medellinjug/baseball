package org.medellinjug.baseball.strategy.model.service;

import org.medellinjug.baseball.strategy.model.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Patricia on 7/5/17.
 * MedellinJUG.org
 */
public class PlayerServiceBean {

    private final CopyOnWriteArrayList<Player> eList = PlaysReader.getInstancePlayer();


    private List<Player>sortedPlayerss(List<Player> playerList) {
        return playerList.stream().sorted(Comparator.comparing(Player::getType).thenComparing(Player::getFullName)).collect(Collectors.toList());
    }
    public List<Player> getAllPlayerss() {
        return this.sortedPlayerss(eList);
    }

    public Player getPlayer(Long id) {
        Player match = null;

        match = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(match);

        return match;
    }


    public  boolean add(Player player) {

        Player newPlayer =
                new Player(player.getId(), player.getFullName(), player.getType());

        eList.add(newPlayer);

        return true;
    }


    public boolean update(Long id, Player player) {
        int matchIndex = -1;

        matchIndex = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> eList.indexOf(e))
                .orElse(matchIndex);

        if (matchIndex > -1) {
            eList.set(matchIndex, player);
            return true;
        } else {
            return false;
        }

    }

    public boolean delete(Long id) {
        int matchIndex = -1;

        matchIndex = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> eList.indexOf(e))
                .orElse(matchIndex);

        if (matchIndex > -1) {
            eList.remove(matchIndex);
            return true;
        } else {
            return false;
        }
    }

}
