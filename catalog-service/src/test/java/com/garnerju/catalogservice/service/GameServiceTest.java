package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GameServiceTest {

    GameService gameService;
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        setUpGameRepositoryMock();
        gameService = new GameService(gameRepository);
        Game game2 = new Game();
        game2.setId(2);
        game2.setTitle("Spider-Man");
        game2.setEsrbRating("4");
        game2.setDescription("Action game");
        game2.setPrice(BigDecimal.valueOf(24.45));
        game2.setStudio("X-Box");
        game2.setQuantity(4);
        System.out.println("GameRepository.save(game) return: " + gameRepository.save(game2));
        System.out.println("GameRepository.findById(game) return: " + gameRepository.findById(Long.valueOf(1)));
        System.out.println("GameRepository.findAll(game) return: " + gameRepository.findAll());
    }

    @Test
    public void justRunTheSetUp() {
        System.out.println("Placeholder");
    }

    private void setUpGameRepositoryMock() {
        gameRepository = mock(GameRepository.class);
        Game game = new Game();
        game.setId(2);
        game.setTitle("Spider-Man");
        game.setEsrbRating("4");
        game.setDescription("Action game");
        game.setPrice(BigDecimal.valueOf(24.45));
        game.setStudio("X-Box");
        game.setQuantity(4);

        Game game2 = new Game();
        game2.setId(2);
        game2.setTitle("Spider-Man");
        game2.setEsrbRating("4");
        game2.setDescription("Action game");
        game2.setPrice(BigDecimal.valueOf(24.45));
        game2.setStudio("X-Box");
        game2.setQuantity(4);


        List<Game> inList = new ArrayList<>();
        inList.add(game);

        doReturn(game).when(gameRepository).save(game2);
        doReturn(Optional.of(game)).when(gameRepository).findById(Long.valueOf(1));
        doReturn(inList).when(gameRepository).findAll();
    }

}