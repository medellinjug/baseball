package org.medellinjug.baseball.strategy.model.entity;

import java.util.List;

/**
 * Created by Hilmer on 30/06/17.
 * MedellinJUG.org
 */
public class Player {

    private Long id;
    private String fullName;
    private Play.Type type;
    private List<Play> playList;

    public Player( Long id, String fullName, Play.Type type) {

        this.id=id;
        this.fullName=fullName;
        this.type=type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Play> getPlayList() {
        return playList;
    }

    public void setPlayList(List<Play> playList) {
        this.playList = playList;
    }

    public Play.Type getType() {
        return type;
    }

    public void setType(Play.Type type) {
        this.type = type;
    }
}
