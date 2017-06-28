package org.medellinjug.baseball.strategy.model.service;

import org.medellinjug.baseball.strategy.model.entity.Play;
import org.medellinjug.baseball.strategy.model.entity.PlayCode;
import org.medellinjug.baseball.strategy.model.entity.Strategy;
import org.medellinjug.baseball.strategy.model.entity.StrategyPlay;
import org.medellinjug.baseball.strategy.model.utils.StrategyMatrix;
import org.medellinjug.baseball.strategy.model.utils.StrategyMatrixCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hilmer on 25/06/17.
 * MedellinJUG.org
 */
public class StrategyServiceBean {

    private final List<Strategy> eList = new ArrayList<>();

    public Strategy createStrategy(Strategy strategy){

        Long next = (this.eList.stream().mapToLong(p->p.getId()).max().orElse(0L))+1;

        strategy = new Strategy(next, new Date(), 0L, strategy.getHeight(), strategy.getType());

        this.generateStrategyPlayss(strategy);

        eList.add(strategy);

        return strategy;
    }


    private static void generateStrategyPlayss(Strategy strategy){

        PlayServiceBean playServiceBean = new PlayServiceBean();
        List<Play> playList = playServiceBean.getPlayssByType(strategy.getType(), null);

            for (Play play : playList) {
                 strategy.getStrategyPlayList()
                        .add(new StrategyPlay(strategy, play, 5L));

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

            int column = 1;
            for(PlayCode playCode:playCodeList1){
                playCode.setPosition(new Long(row), new Long(column++));
            }
            row++;
        }




        if(true) {

            String defaultColor = "#FFFFFF";

            StrategyMatrix strategyMatrix = new StrategyMatrix();
            strategyMatrix.setStrategyMatrixCellListHeader(new ArrayList<>());
            strategyMatrix.setStrategyMatrixCellListValue(new ArrayList<>());

            int column = 1;
            strategyMatrix.getStrategyMatrixCellListHeader().add(new StrategyMatrixCell(String.valueOf(" "), null));
            for (int it = 0; it < strategy.getWidth(); it++) {
                strategyMatrix.getStrategyMatrixCellListHeader().add(new StrategyMatrixCell(String.valueOf(column++), null));
            }


            row = 1;
            for (List<PlayCode> playCodeList1 : playCodeMatrix) {

                List<StrategyMatrixCell> strategyMatrixCellListValue = new ArrayList<>();
                strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(row++), defaultColor));

                for (PlayCode playCode1 : playCodeList1) {
                    strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(playCode1.getStrategyPlay().getPlay().getCode()), playCode1.getStrategyPlay().getPlay().getColor()));

                }
                if(playCodeList1.size()<strategy.getWidth()){

                    for (int it = playCodeList1.size(); it < strategy.getWidth(); it++) {
                        strategyMatrixCellListValue.add(new StrategyMatrixCell(String.valueOf(" "), defaultColor));
                    }

                }
                strategyMatrix.getStrategyMatrixCellListValue().add(strategyMatrixCellListValue);

            }

            strategy.setStrategyMatrix(strategyMatrix);
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


}
