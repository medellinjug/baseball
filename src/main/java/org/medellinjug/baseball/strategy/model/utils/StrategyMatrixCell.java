package org.medellinjug.baseball.strategy.model.utils;

/**
 * Created by Hilmer on 25/06/17.
 * MedellinJUG.org
 */
public class StrategyMatrixCell {
    private String value;
    private String color;

    public StrategyMatrixCell(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



}
