package org.medellinjug.baseball.strategy.view;


import java.util.List;

import org.medellinjug.baseball.strategy.model.entity.Play;
import org.medellinjug.baseball.strategy.model.entity.Strategy;
import org.medellinjug.baseball.strategy.model.entity.StrategyPlay;
import org.medellinjug.baseball.strategy.model.service.PlayServiceBean;
import org.medellinjug.baseball.strategy.model.service.StrategyServiceBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by Amy on 25/06/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/baseball")
public class StrategyController {


    PlayServiceBean playServiceBean = new PlayServiceBean();
    StrategyServiceBean strategyServiceBean = new StrategyServiceBean();

    // Get all plays
    @RequestMapping(method = RequestMethod.GET, value = "/play")
    public Play[] getAll() {

        return playServiceBean.getAllPlayss().toArray(new Play[0]);
    }

    // Get a play
    @RequestMapping(method = RequestMethod.GET, value = "/play/{id}")
    public ResponseEntity get(@PathVariable Long id) {

        Play match = null;
        match = playServiceBean.getPlay(id);

        if (match != null) {
            return new ResponseEntity<>(match, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get employee by title
    @RequestMapping(method = RequestMethod.GET, value = "/play/plays/{type}&{name}")
    public ResponseEntity getByType(@PathVariable Play.Type type, @PathVariable String name) {
        List<Play> matchList = playServiceBean.getPlayssByType(type, name);


        if (matchList.size() > 0) {
            return new ResponseEntity<>(matchList.toArray(new Play[0]), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    // Add a play
    @RequestMapping(method = RequestMethod.POST, value="/play")
    public ResponseEntity add(@RequestBody Play play) {
        if (playServiceBean.add(play)) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a play
    @RequestMapping(method = RequestMethod.PUT, value = "/play/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Play play) {

        if (playServiceBean.update(id, play)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    // Delete a play
    @RequestMapping(method = RequestMethod.DELETE, value = "/play/{id}")
    public ResponseEntity delete(@PathVariable Long id, @RequestBody Play play) {

        if (playServiceBean.delete(id)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get a new strategy
    @RequestMapping(method = RequestMethod.POST, value = "/strategy")
    public ResponseEntity addStrategy(@RequestBody Strategy strategy){

         strategy = strategyServiceBean.createStrategy(strategy);


        if (strategy != null) {
            return new ResponseEntity<>(strategy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Process a strategy
    @RequestMapping(method = RequestMethod.PUT, value = "/strategy/{id}")
    public ResponseEntity processStrategy(@PathVariable Long id, @RequestBody Strategy strategy){


        Strategy currentStrategy = strategyServiceBean.processStrategy(id, strategy);
        if (strategy != null) {
            //return new ResponseEntity<Strategy>(currentStrategy, HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

          /*http://websystique.com/spring-boot/spring-boot-rest-api-example/
        if (playServiceBean.processStrategy(strategy)) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }


    // Get a new strategy
    @RequestMapping(method = RequestMethod.GET, value = "/strategy/{id}")
    public ResponseEntity getStrategy(@PathVariable Long id){

        Strategy strategy = strategyServiceBean.getStrategy(id);

        if (strategy != null) {
            return new ResponseEntity<>(strategy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get a strategyPlay
    @RequestMapping(method = RequestMethod.GET, value = "/strategy/play/{idStrategy}&{idPlay}")
    public ResponseEntity getStrategyPlay(@PathVariable Long idStrategy, @PathVariable Long idPlay) {

        StrategyPlay match = null;
        match = strategyServiceBean.getStrategyPlay(idStrategy, idPlay);

        if (match != null) {
            return new ResponseEntity<>(match, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update a strategy
    @RequestMapping(method = RequestMethod.PUT, value = "/strategy/play/{idStrategy}")
    public ResponseEntity update(@PathVariable Long idStrategy, @RequestBody StrategyPlay strategyPlay) {

        if (strategyServiceBean.updateStrategyPlay(idStrategy, strategyPlay)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }






}
