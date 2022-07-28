package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }


    public Game findById(long id) {
        return gameRepository.findById(id).orElse(null);
    }


    public Game createGame(Game newGame) {
        return gameRepository.save(newGame);
    }

    public Game updateGame(@RequestBody Game game) {return gameRepository.save(game);}

    public void deleteGame(long id) {
        gameRepository.deleteById(id);
    }
}
