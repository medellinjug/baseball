package org.medellinjug.baseball.strategy;

import org.medellinjug.baseball.strategy.model.entity.Play;
import org.medellinjug.baseball.strategy.model.entity.PlayCode;
import org.medellinjug.baseball.strategy.model.entity.Strategy;
import org.medellinjug.baseball.strategy.model.entity.StrategyPlay;
import org.medellinjug.baseball.strategy.model.service.PlayServiceBean;

import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hilmer on 24/06/17.
 */
@Deprecated
public class StrategyController {


    public static void main(String[] args){

        System.out.println("Welcome to the baseball strategy generator");



        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
          //  System.exit(1);
        }

        String rows = c==null?"3" :c.readLine("Enter the height: ");
        //char [] oldPassword = c.readPassword("Enter your old password: ");


        Strategy strategy = new Strategy(0L, new Date(), 0L, new Long(rows), Play.Type.HIT);
        if(true){
            inputData(strategy);
        }else {
            loadData(strategy);
        }
        strategy.calculateSize();
        //System.out.println(strategy);

        List<PlayCode> playCodeList
                = strategy.getStrategyPlayList().stream().map(p->p.getPlayCodeList()).flatMap(q->q.stream()).collect(Collectors.toList());

        //playCodeList.forEach(System.out::println);

        Collections.shuffle(playCodeList);

        //playCodeList.forEach(System.out::println);



        List<List<PlayCode>> playCodeMatrix =  new ArrayList<>();
        //System.out.println("playCodeList  " + playCodeList.size());
        for(int it=0; it < playCodeList.size(); ){

            int toIndex = it + strategy.getWidth().intValue();

            if(toIndex>playCodeList.size()){
                toIndex = playCodeList.size();
            }
            //System.out.println("get:: From= " + it + " to " + toIndex);
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


        //playCodeMatrix.forEach(System.out::println);


        System.out.println("MATRIX results");
        if(true){

            List<Long> headerList = new ArrayList<>();
            int column = 1;
            for(int it=0; it < strategy.getWidth(); it++){
                headerList.add(new Long(column++));
            }
            System.out.print(" " + ";");
            System.out.println(headerList.stream().map(p->p.toString()).collect(Collectors.joining(";")));
        }
         row = 1;
        for(List<PlayCode> playCodeList1:playCodeMatrix){
            System.out.print(row++ + ";");
            System.out.println(playCodeList1.stream()
                    .map(p->p.getStrategyPlay().getPlay().getCode()).collect(Collectors.joining(";")));
        }


        System.out.println("Table results");

        Map<Play, List<PlayCode>> mp =
                playCodeList.stream().collect( Collectors.groupingBy(p->p.getStrategyPlay().getPlay()));


        for(Map.Entry<Play, List<PlayCode>> mpSet:mp.entrySet()){
            System.out.print("Code for: " + mpSet.getKey());
            System.out.println(mpSet.getValue().stream()
                    .map(p->p.getPosition()).collect(Collectors.joining(";")));
        }


    }


    private static void inputData(Strategy strategy){
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            //System.exit(1);
        }
        PlayServiceBean playServiceBean = new PlayServiceBean();
        List<Play> playList = playServiceBean.getAllPlayss();
        for(Play play:playList){



            String quantity = c == null?"5":c.readLine("Enter the number of plays for: " + play);


            strategy.getStrategyPlayList()
                    .add(new StrategyPlay(strategy, play, new Long(quantity)));

        }

        }

    @Deprecated
    private static void loadData(Strategy strategy){
        Long quantity = 5L;
        strategy.getStrategyPlayList()
                .add(new StrategyPlay(strategy, new Play(1L, "RRF","Rolling right field", Play.Type.HIT, null),quantity));

        strategy.getStrategyPlayList()
                .add(new StrategyPlay(strategy, new Play(2L, "RCF","Rolling center field", Play.Type.HIT, null), quantity));

        strategy.getStrategyPlayList()
                .add(new StrategyPlay(strategy, new Play(3L, "RLF","Rolling left field", Play.Type.HIT, null), quantity));


        strategy.getStrategyPlayList()
                .add(new StrategyPlay(strategy, new Play(1L, "FRF","Fly right field", Play.Type.HIT, null),quantity));

        strategy.getStrategyPlayList()
                .add(new StrategyPlay(strategy, new Play(4L, "FCF","Fly center field", Play.Type.HIT, null),quantity));

        strategy.getStrategyPlayList()
                .add(new StrategyPlay(strategy, new Play(5L, "FLF","Fly left field", Play.Type.HIT, null),7L));
    }
}
