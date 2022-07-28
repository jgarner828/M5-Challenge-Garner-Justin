package com.garnerju.catalogservice.controllers;

import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class GameController {
    
    @Autowired
    GameService gameService;

    @GetMapping("/games")
    public List<Game> getGame() { return gameService.findAllGames(); }

    @GetMapping("/games/{id}")
    public Game getGameById(long id) {return gameService.findById(id);}

    @PostMapping("/games")
    public Game createGame(@RequestBody Game game) {return gameService.createGame(game);}

    @PutMapping("/games")
    public Game updateGame(@RequestBody Game game) {return gameService.updateGame(game);}

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable long id) {gameService.deleteGame(id);}
}
