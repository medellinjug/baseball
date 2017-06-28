package org.medellinjug.baseball.strategy.model.entity;

import org.medellinjug.baseball.strategy.model.utils.StrategyMatrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amy on 24/06/17.
 */
public class Strategy{

    private Long id;
    private Date date;
    private Long width;
    private Long height;
    private Play.Type type;
    private List<StrategyPlay> strategyPlayList;
    private StrategyMatrix strategyMatrix;

    public Strategy() {
        super();
        /*this.id = 0L;
        this.date = null;
        this.width = null;
        this.height = height;
        this.type = type;
        this.strategyPlayList = new ArrayList<>();*/
    }

    public Strategy(Long id, Date date, Long width, Long height, Play.Type type) {
        this.id = id;
        this.date = date;
        this.width = width;
        this.height = height;
        this.type = type;
        this.strategyPlayList = new ArrayList<>();
    }


    public void setStrategyPlayList(List<StrategyPlay> strategyPlayList) {
        this.strategyPlayList = strategyPlayList;
    }

    public List<StrategyPlay> getStrategyPlayList() {
        return strategyPlayList;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Play.Type getType() {
        return type;
    }

    public void setType(Play.Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StrategyMatrix getStrategyMatrix() {
        return strategyMatrix;
    }

    public void setStrategyMatrix(StrategyMatrix strategyMatrix) {
        this.strategyMatrix = strategyMatrix;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "date=" + date +
                ", width=" + width +
                ", height=" + height +
                ", strategyPlayList=" + strategyPlayList +
                '}';
    }

    private Long getNumberOfCodes(){

        return this.getStrategyPlayList().stream().mapToLong(p->p.getQuantity()).sum();
    }

    public void calculateSize(){
        //this.height = 5L;
        this.width = this.getNumberOfCodes()/this.height;
    }


}
