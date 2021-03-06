package org.medellinjug.baseball.strategy.model.utils;

import org.medellinjug.baseball.strategy.model.entity.Player;

import java.util.List;

/**
 * Created by Hilmer on 25/06/17.
 * MedellinJUG.org
 */
public class StrategyMatrix {

    private Player player;

    private List<StrategyMatrixCell> strategyMatrixCellListHeader;
    private List<List<StrategyMatrixCell>> strategyMatrixCellListValue;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<StrategyMatrixCell> getStrategyMatrixCellListHeader() {
        return strategyMatrixCellListHeader;
    }

    public void setStrategyMatrixCellListHeader(List<StrategyMatrixCell> strategyMatrixCellListHeader) {
        this.strategyMatrixCellListHeader = strategyMatrixCellListHeader;
    }

    public List<List<StrategyMatrixCell>> getStrategyMatrixCellListValue() {
        return strategyMatrixCellListValue;
    }

    public void setStrategyMatrixCellListValue(List<List<StrategyMatrixCell>> strategyMatrixCellListValue) {
        this.strategyMatrixCellListValue = strategyMatrixCellListValue;
    }
}
