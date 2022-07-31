package com.garnerju.catalogservice.controllers;

import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RefreshScope
public class GameController {
    
    @Autowired
    GameService gameService;

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGame(@PathParam("studio") String studio, @PathParam("esrbRating") String esrbRating, @PathParam("title") String title) {
        if(studio == null && esrbRating == null) return gameService.findGameByTitle(title);
        if(studio == null && title == null) return gameService.findGameByESRB(esrbRating);
        if(title == null && esrbRating == null) return gameService.findGameByStudio(studio);

        return gameService.findAllGames(); }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGameById(Long id) {return gameService.findById(id);}

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody @Valid Game game) {
        if(game.getPrice() == null || game.getPrice().equals(BigDecimal.valueOf(0)))throw new RuntimeException("Must have a price");
        if(game.getQuantity() < 1) throw new RuntimeException("Must have a quantity");
        if(game.getTitle() == null) throw new RuntimeException("Must have a title");
        return gameService.createGame(game);}

    @PutMapping("/games")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Game updateGame(@RequestBody @Valid  Game game) {
        if(game.getPrice() == null || game.getPrice().equals(BigDecimal.valueOf(0)))throw new RuntimeException("Must have a price");
        if(game.getQuantity() < 1) throw new RuntimeException("Must have a quantity");
        if(game.getTitle() == null) throw new RuntimeException("Must have a title");
        return gameService.updateGame(game);
    }

    @DeleteMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable Long id) {gameService.deleteGame(id);}
}
