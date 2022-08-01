package com.garnerju.catalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.repository.GameRepository;
import com.garnerju.catalogservice.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;
    @MockBean
    GameRepository gameRepository;

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void ShouldGetGames() throws Exception {

        List<Game> expectedGame = new ArrayList<>(Collections.singletonList(new Game(1, "Dr. Mario", "6", "Rescue the queen", new BigDecimal("9.99"), "Nintendo", 5)));
        Mockito.when(gameService.findAllGames()).thenReturn(expectedGame);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/game")) //Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void ShouldGetGamesByTitle() throws Exception {

        List<Game> expectedGame = new ArrayList<>(Collections.singletonList(new Game(1, "Dr. Mario", "6", "Rescue the queen", new BigDecimal("9.99"), "Nintendo", 5)));
        Mockito.when(gameService.findGameByTitle("Dr. Mario")).thenReturn(expectedGame);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/game?title=Dr. Mario")) //Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }


    @Test
    public void ShouldGetGamesByEsrbRating() throws Exception {

        List<Game> expectedGame = new ArrayList<>(Collections.singletonList(new Game(1, "Dr. Mario", "5", "Rescue the queen", new BigDecimal("9.99"), "Nintendo", 5)));
        Mockito.when(gameService.findGameByESRB("5")).thenReturn(expectedGame);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/game?esrb=5")) //Act
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldGetGamesByStudio() throws Exception {

        List<Game> expectedGame = new ArrayList<>(Arrays.asList(new Game(1,"Dr. Mario","6","Rescue the queen",new BigDecimal("9.99"),"Nintendo",5)));
        Mockito.when(gameService.findGameByStudio("Nintendo")).thenReturn(expectedGame);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/game?studio=Nintendo")) //Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }


    @Test
    public void ShouldGetGameById() throws Exception{

        Game expectedGame = new Game(1,"Dr. Mario","6","Rescue the queen",new BigDecimal("9.99"),"Nintendo",5);
        Mockito.when(gameService.findById(1)).thenReturn(expectedGame);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/game/1")) //Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void shouldCreateGame() throws Exception{

        Game inputBody = new Game("Dr. Mario","6","Rescue the queen",new BigDecimal("9.99"),"Nintendo",5);
        Game expectedGame = new Game(1,"Dr. Mario","6","Rescue the queen",new BigDecimal("9.99"),"Nintendo",5);
        Mockito.when(gameService.createGame(inputBody)).thenReturn(expectedGame);
        String expectedJson = mapper.writeValueAsString(expectedGame);
        String inputJson = mapper.writeValueAsString(inputBody);

        mockMvc.perform(post("/game")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                //Act
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void ShouldDeleteGameById() throws Exception{


        mockMvc.perform(delete("/game/2")) //Act
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    public void ShouldReturn422UProcessableWhenSearchingGameByIdWithAwrongEntryType() throws Exception{
        mockMvc.perform(get("/game/rt")) //Act
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void ShouldReturn404NotFoundForMisSpelledUrl() throws Exception{



        mockMvc.perform(get("/gamfasdf")) //Act  //miss-spelled url
                .andDo(print())
                .andExpect(status().isNotFound());

    }



}