package org.medellinjug.baseball.strategy.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amy on 24/06/17.
 */
public class StrategyPlay {
    private Play play;
    //private Strategy strategy;
    private Long idStrategy;
    private Long quantity;
    private List<PlayCode> playCodeList;

    public StrategyPlay() {
        super();
        this.playCodeList = new ArrayList<>();
    }

    public StrategyPlay(Strategy strategy, Play play, Long quantity) {
        this.idStrategy = strategy.getId();
        this.play = play;
        this.quantity = quantity;
        this.playCodeList = new ArrayList<>();

    }

    public Long getIdStrategy() {
        return idStrategy;
    }

    public void setIdStrategy(Long idStrategy) {
        this.idStrategy = idStrategy;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
/*
    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }*/


    public List<PlayCode> getPlayCodeList() {
        return playCodeList;
    }

    public void setPlayCodeList(List<PlayCode> playCodeList) {
        this.playCodeList = playCodeList;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    public void createPlayCodess(){
        if(quantity!=null) {
            for (int it = 0; it < quantity.intValue(); it++) {
                this.playCodeList.add(new PlayCode(this));
            }
        }
    }


    //https://stackoverflow.com/questions/18357370/angularjs-building-a-dynamic-table-based-on-a-json
}
