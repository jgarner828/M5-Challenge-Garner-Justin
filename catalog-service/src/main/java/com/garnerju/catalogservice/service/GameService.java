package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.errors.NotFoundException;
import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.models.TShirt;
import com.garnerju.catalogservice.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
    }

    public List<Game> findAllGames() {
        List<Game> gameList = gameRepository.findAll();

        if (gameList == null || gameList.isEmpty()) {
            throw new IllegalArgumentException("No games were found.");
        } else {
            return gameList;
        }
    }

    public Game findById(long id) {
        return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("No games were found."));
    }

    public Game createGame(Game newGame) {
        return gameRepository.save(newGame);
    }

    public Game updateGame(Game game) {      //Validate incoming Game Data in the view model
        if (game == null) {
            throw new RuntimeException("No Game data is passed! Game object is null!");
        }
        //make sure the game exists. and if not, throw exception...
        else if (this.findById(game.getId()) == null) {
            throw new RuntimeException("No such game to update.");
        }
         return gameRepository.save(game);
    }

    public void deleteGame(long id) {
        gameRepository.deleteById(id);
    }

    public List<Game> findGameByTitle(String title) {

        List<Game> gameList = gameRepository.findAllByTitle(title);

        if (gameList == null || gameList.isEmpty()) {
            throw new RuntimeException("No games were found.");
        } else {
            return gameList;
        }}

    public List<Game> findGameByESRB(String esrb) {
        List<Game> gameList = gameRepository.findAllByEsrbRating(esrb);

        if (gameList == null || gameList.isEmpty()) {
            throw new RuntimeException("No games with that ESRB rating were found.");
        } else {
            return gameList;
        }}

    public List<Game> findGameByStudio(String studio) {

        List<Game> gameList = gameRepository.findAllByStudio(studio);

        if (gameList == null || gameList.isEmpty()) {
            throw new RuntimeException("No games made by that studio were found.");
        } else {
            return gameList;
        }
    }
}
