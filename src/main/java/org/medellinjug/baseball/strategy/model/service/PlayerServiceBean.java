package org.medellinjug.baseball.strategy.model.service;

import org.medellinjug.baseball.strategy.model.entity.Play;
import org.medellinjug.baseball.strategy.model.entity.Player;

import java.util.ArrayList;
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


    public List<Player> getPlayerssByType(Play.Type type, String name) {
        if (type != null && name != null && name.length() > 0) {
            return eList.stream().filter(p -> p.getType().equals(type) && p.getFullName().toUpperCase().contains(name.toUpperCase())).collect(Collectors.toList());
        } else if (type != null) {
            return eList.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());
        } else if (name != null && name.length() > 0) {
            return eList.stream().filter(p -> p.getFullName().toUpperCase().contains(name.toUpperCase())).collect(Collectors.toList());

        } else {
            return eList;
        }
    }




    public  boolean add(Player player) {
        Long next = (this.eList.stream().mapToLong(p -> p.getId()).max().orElse(0L)) + 1;



        Player newPlayer =
                new Player(next, player.getFullName(), player.getType(), player.getPlayList());

        eList.add(newPlayer);

        return true;
    }




    public boolean update(Long id, Player player) {
        int matchIndex = -1;
        if(player.getPlayList()==null){
            player.setPlayList(new ArrayList<>());
        }

        player.setPlayss(player.getPlayList().stream().map(p->p.getCode()).collect(Collectors.toList()));
        matchIndex = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> eList.indexOf(e))
                .orElse(matchIndex);

        if (matchIndex > -1) {
            eList.set(matchIndex, player);

            PlaysReader.createPlayerss();

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
            PlaysReader.createPlayerss();
            return true;
        } else {
            return false;
        }
    }

}
