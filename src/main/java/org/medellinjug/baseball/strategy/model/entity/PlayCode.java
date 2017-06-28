package org.medellinjug.baseball.strategy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Hilmer on 24/06/17.
 * MedellinJUG.org
 */
public class PlayCode {
    private StrategyPlay strategyPlay;

    private Long row;
    private Long column;

    public PlayCode(StrategyPlay strategyPlay) {
        this.strategyPlay = strategyPlay;
    }

    @JsonIgnore
    public StrategyPlay getStrategyPlay() {
        return strategyPlay;
    }

    public void setStrategyPlay(StrategyPlay strategyPlay) {
        this.strategyPlay = strategyPlay;
    }


    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getColumn() {
        return column;
    }

    public void setColumn(Long column) {
        this.column = column;
    }


    @Override
    public String toString() {
        return "PlayCode{" +
                " Play=" + strategyPlay.getPlay() +
                ", code=" + this.row + this.column +
                '}';
    }



    public void setPosition(Long row, Long column){
        this.row = row;
        this.column = column;
    }

    public String getPosition(){
        return this.column + "_" +this.row;
    }
}
