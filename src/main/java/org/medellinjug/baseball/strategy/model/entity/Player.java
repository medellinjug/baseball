package org.medellinjug.baseball.strategy.model.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hilmer on 30/06/17.
 * MedellinJUG.org
 */
@JsonFilter("myFilter")
public class Player {


    private Long id;

    private String fullName;

    private Play.Type type;
    private List<Play> playList;

    private List<String> playss;

    public Player() {
        super();
    }

    public Player(Long id, String fullName, Play.Type type, List<Play> playList) {

        this.id=id;
        this.fullName=fullName;
        this.type=type;
        this.playList=playList;

        if(this.playList==null){
            this.playList = new ArrayList<>();
        }

        this.playss =  this.playList.stream().map(p->p.getCode()).collect(Collectors.toList());
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

    public List<String> getPlayss() {
        return playss;
    }

    public void setPlayss(List<String> playss) {
        this.playss = playss;
    }
}
