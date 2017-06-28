package org.medellinjug.baseball.strategy.model.service;

import org.medellinjug.baseball.strategy.model.entity.Play;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Amy on 25/06/17.
 */
public class PlayServiceBean {

    private final CopyOnWriteArrayList<Play> eList = PlaysReader.getInstance();

    private List<Play> sortedPlayss(List<Play> playList) {
        return playList.stream().sorted(Comparator.comparing(Play::getType).thenComparing(Play::getName)).collect(Collectors.toList());
    }

    public List<Play> getAllPlayss() {
        return this.sortedPlayss(eList);
    }

    public Play getPlay(Long id) {
        Play match = null;

        match = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(match);

        return match;
    }


    public List<Play> getPlayssByType(Play.Type type, String name) {
        if (type != null && name != null && name.length() > 0) {
            return eList.stream().filter(p -> p.getType().equals(type) && p.getName().toUpperCase().contains(name.toUpperCase())).collect(Collectors.toList());
        } else if (type != null) {
            return eList.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());
        } else if (name != null && name.length() > 0) {
            return eList.stream().filter(p -> p.getName().toUpperCase().contains(name.toUpperCase())).collect(Collectors.toList());

        } else {
            return eList;
        }
    }


    public boolean add(Play play) {
        Long next = (this.eList.stream().mapToLong(p -> p.getId()).max().orElse(0L)) + 1;

        Play nextEmployee =
                new Play(next, play.getCode(), play.getName(),
                        play.getType(), null);

        eList.add(nextEmployee);

        return true;
    }


    public boolean update(Long id, Play play) {
        int matchIndex = -1;

        matchIndex = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> eList.indexOf(e))
                .orElse(matchIndex);

        if (matchIndex > -1) {
            eList.set(matchIndex, play);
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
