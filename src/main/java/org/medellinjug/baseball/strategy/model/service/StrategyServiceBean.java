package org.medellinjug.baseball.strategy.model.service;

import org.medellinjug.baseball.strategy.model.entity.*;
import org.medellinjug.baseball.strategy.model.exception.PlayerNoFoundException;
import org.medellinjug.baseball.strategy.model.utils.StrategyMatrix;
import org.medellinjug.baseball.strategy.model.utils.StrategyMatrixCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Hilmer on 25/06/17.
 * MedellinJUG.org
 */
public class StrategyServiceBean {

    private final List<Strategy> eList = new ArrayList<>();


    public List<Strategy> getStrategiesByType(Play.Type type) {
        if (type != null) {
            return eList.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());
        } else {
            return eList;
        }
    }


    public Strategy createStrategy(Strategy strategy) throws PlayerNoFoundException {

        Play.Type type = strategy.getType();

        List<Player> playerList = this.ePlayerList.stream().filter(p->p.getType().equals(type)).collect(Collectors.toList());

        if(playerList.isEmpty()){
            throw new PlayerNoFoundException("No playes for " + type);
        }

        Long next = (this.eList.stream().mapToLong(p->p.getId()).max().orElse(0L))+1;

        strategy = new Strategy(next, new Date(), 0L, strategy.getRows(), strategy.getType());


        strategy.setPlayerList(playerList);

        this.generateStrategyPlayss(strategy);

        eList.add(strategy);

        return strategy;
    }


    private static void generateStrategyPlayss(Strategy strategy){
/*
        PlayServiceBean playServiceBean = new PlayServiceBean();
        List<Play> playList = playServiceBean.getPlayssByType(strategy.getType(), null);
       */
        List<Play> playList = strategy.getPlayerList().stream().map(p->p.getPlayList()).flatMap(q->q.stream()).distinct().collect(Collectors.toList());

        for (Play play : playList) {
            strategy.getStrategyPlayList().add(new StrategyPlay(strategy, play, 3L));
        }


    }

    public Strategy getStrategy(Long id){
        Strategy match = null;

        match =  eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(null);

        return match;
    }

    public StrategyPlay getStrategyPlay(Long idStrategy, Long idPlay){
        Strategy strategy = this.getStrategy(idStrategy);

        return   strategy.getStrategyPlayList().stream()
                .filter(e -> e.getPlay().getId().equals(idPlay))
                .findFirst().orElse(null);


    }

    public boolean updateStrategyPlay(Long idStrategy, StrategyPlay strategyPlay) {
        Strategy strategy = this.getStrategy(idStrategy);

        int matchIndex = -1;



        matchIndex = strategy.getStrategyPlayList().stream()
                .filter(e -> e.getPlay().getId().equals(strategyPlay.getPlay().getId()))
                .findFirst()
                .map(e -> strategy.getStrategyPlayList().indexOf(e))
                .orElse(matchIndex);

        if (matchIndex > -1) {
            strategy.getStrategyPlayList().set(matchIndex, strategyPlay);
            return true;
        } else {
            return false;
        }



    }


    public Strategy processStrategy(Long id, Strategy strategy){

       // strategy = this.getStrategy(id);


        strategy.calculateSize();

        strategy.getStrategyPlayList().forEach(p->p.createPlayCodess());


        List<PlayCode> playCodeList
                = strategy.getStrategyPlayList().stream().map(p->p.getPlayCodeList()).flatMap(q->q.stream()).collect(Collectors.toList());


        Collections.shuffle(playCodeList);



        List<List<PlayCode>> playCodeMatrix =  new ArrayList<>();
         for(int it=0; it < playCodeList.size(); ){

            int toIndex = it + strategy.getWidth().intValue();

            if(toIndex>playCodeList.size()){
                toIndex = playCodeList.size();
            }
             List<PlayCode> playCodeListRow = playCodeList.subList(it, toIndex);
            playCodeMatrix.add(playCodeListRow);
            it = toIndex;
        }

        int row = 1;

        for(List<PlayCode> playCodeList1:playCodeMatrix){

            int column = 11;
            for(PlayCode playCode:playCodeList1){
                playCode.setPosition(new Long(row), new Long(column++));
                if(column%10==0){
                    column ++;
                }
            }
            row++;
        }

        List<Player> playerList = this.ePlayerList.stream().filter(p->p.getType().equals(strategy.getType())).collect(Collectors.toList());
        if(!playerList.isEmpty()) {
            strategy.setStrategyMatrixList(new ArrayList<>());

            for (Player player : playerList) {
                StrategyMatrix strategyMatrix = this.generateStrategyMatrix(playCodeMatrix, player, strategy.getWidth());

                if (strategyMatrix != null) {
                    strategy.getStrategyMatrixList().add(strategyMatrix);
                }
            }
        }


        int matchIndex = -1;

        matchIndex = eList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> eList.indexOf(e))
                .orElse(matchIndex);

        if (matchIndex > -1) {
            eList.set(matchIndex, strategy);

        }



        return strategy;
    }


    private StrategyMatrix generateStrategyMatrix(List<List<PlayCode>> playCodeMatrix, Player player, Long cols ){

        String defaultColor = "#FFFFFF";

        StrategyMatrix strategyMatrix = new StrategyMatrix();
        strategyMatrix.setPlayer(player);
        strategyMatrix.setStrategyMatrixCellListHeader(new ArrayList<>());
        strategyMatrix.setStrategyMatrixCellListValue(new ArrayList<>());

        int column = 11;
        strategyMatrix.getStrategyMatrixCellListHeader().add(new StrategyMatrixCell(String.valueOf(" "), null));

        for (int it = 0; it < cols; it++) {
            strategyMatrix.getStrategyMatrixCellListHeader().add(new StrategyMatrixCell(String.valueOf(column++), null));

            if(column%10==0){
                column ++;
            }
        }

        int row = 1;

        for (List<PlayCode> playCodeList : playCodeMatrix) {

            List<StrategyMatrixCell> strategyMatrixCellListValue = new ArrayList<>();
            strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(row++), defaultColor));

            for (PlayCode playCode : playCodeList) {
                if(player.getPlayList().stream().anyMatch(p->p.getId().equals(playCode.getStrategyPlay().getPlay().getId()))) {
                    strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(playCode.getStrategyPlay().getPlay().getCode()), playCode.getStrategyPlay().getPlay().getColor()));
                }else{
                    strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(" "), defaultColor));
                }
            }
            if(playCodeList.size()< cols){

                for (int it = playCodeList.size(); it < cols; it++) {
                    strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(" "), defaultColor));
                }

            }
            strategyMatrix.getStrategyMatrixCellListValue().add(strategyMatrixCellListValue);

        }

        return strategyMatrix;

    }



    private final CopyOnWriteArrayList<Player> ePlayerList = PlaysReader.getInstancePlayer();

}
