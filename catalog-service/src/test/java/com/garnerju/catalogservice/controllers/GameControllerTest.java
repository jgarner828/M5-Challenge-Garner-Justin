package com.garnerju.catalogservice.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.garnerju.catalogservice.models.Game;
import com.garnerju.catalogservice.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private GameService storeServiceLayer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldCreateGame() throws Exception{

        String outputJson = null;
        String inputJson=null;

        //Arrange
        Game inGame = new Game();
        inGame.setTitle("Halo");
        inGame.setEsrbRating("E10+");
        inGame.setDescription("Puzzles and Math");
        inGame.setPrice(new BigDecimal("23.99"));
        inGame.setStudio("Xbox Game Studios");
        inGame.setQuantity(5);

        inputJson = mapper.writeValueAsString(inGame);
        System.out.println(inputJson);

        Game savedGame = new Game();
        savedGame.setTitle("Halo");
        savedGame.setEsrbRating("E10+");
        savedGame.setDescription("Puzzles and Math");
        savedGame.setPrice(new BigDecimal("23.99"));
        savedGame.setStudio("Xbox Game Studios");
        savedGame.setQuantity(5);
        savedGame.setId(51);

        outputJson = mapper.writeValueAsString(savedGame);

        //Mock call to service layer...
        when(storeServiceLayer.createGame(inGame)).thenReturn(savedGame);

        //Act & Assert
        this.mockMvc.perform(post("/games")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    //    TODO NEED TO TROUBLESHOOT WHY IT IS RETURNING IMPROPERLY.
//    @Test
//    public void shouldReturnGameInfo() throws Exception{
//
//        //Object to JSON in String
//        String outputJson = null;
//
//        //Arrange
//        Game game =
//                new Game(Long.valueOf(1), "PacMan", "5", "Eat things", BigDecimal.valueOf(10.00), "Pacman", 4);
//
//
//
//        when(storeServiceLayer.findById(1)).thenReturn(game);
//
//        //Act & Assert
//        this.mockMvc.perform(get("/games/1"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }

    @Test
    public void shouldFailGetGameBadIdReturns404() throws Exception {

        String outputJson = null;
        long idForGameThatDoesNotExist = 100;

        //Arrange
        Game game = new Game();
        outputJson = mapper.writeValueAsString(game);
        when(storeServiceLayer.findById(idForGameThatDoesNotExist)).thenReturn(null);

        //Act & Assert
        this.mockMvc.perform(get("/games/" + idForGameThatDoesNotExist))
                .andDo(print())
                .andExpect(status().is4xxClientError()); //Expected response status code.
    }

    @Test
    public void shouldUpdateGame() throws Exception{
        //Object to JSON in String
        String inputJson=null;

        //Arrange
        Game inGame = new Game();
        inGame.setTitle("Halo");
        inGame.setEsrbRating("E10+");
        inGame.setDescription("Puzzles and Math");
        inGame.setPrice(new BigDecimal("23.99"));
        inGame.setStudio("Xbox Game Studios");
        inGame.setQuantity(5);
        inGame.setId(62);

        inputJson = mapper.writeValueAsString(inGame);



        //Act & Assert
        this.mockMvc.perform(put("/games")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteGame() throws Exception{
        //Object to JSON in String
        String inputJson=null;

        //Arrange
        Game inGame = new Game();
        inGame.setTitle("Halo");
        inGame.setEsrbRating("E10+");
        inGame.setDescription("Puzzles and Math");
        inGame.setPrice(new BigDecimal("23.99"));
        inGame.setStudio("Xbox Game Studios");
        inGame.setQuantity(5);
        inGame.setId(62);

        inputJson = mapper.writeValueAsString(inGame);

        //Mock call to service layer...
        //Nothing to mock!
        //Checking checking for the correct response status code
        doNothing().when(storeServiceLayer).deleteGame(62);

        //Act & Assert
        this.mockMvc.perform(delete("/games/62"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetGamesByTitle() throws Exception{
        //Object to JSON in String
        String outputJson = null;

        //Arrange
        Game savedGame1 = new Game();
        savedGame1.setTitle("Halo");
        savedGame1.setEsrbRating("E10+");
        savedGame1.setDescription("Puzzles and Math");
        savedGame1.setPrice(new BigDecimal("23.99"));
        savedGame1.setStudio("Xbox Game Studios");
        savedGame1.setQuantity(5);
        savedGame1.setId(56);

        Game savedGame2 = new Game();
        savedGame2.setTitle("Halo I");
        savedGame2.setEsrbRating("E10+");
        savedGame2.setDescription("Puzzles and Math");
        savedGame2.setPrice(new BigDecimal("23.99"));
        savedGame2.setStudio("Xbox Game Studios");
        savedGame2.setQuantity(5);
        savedGame2.setId(51);

        Game savedGame3 = new Game();
        savedGame3.setTitle("Halo IV");
        savedGame3.setEsrbRating("E10+");
        savedGame3.setDescription("Puzzles and Math");
        savedGame3.setPrice(new BigDecimal("23.99"));
        savedGame3.setStudio("Xbox Game Studios");
        savedGame3.setQuantity(5);
        savedGame3.setId(77);

        List<Game> foundList = new ArrayList();
        foundList.add(savedGame1);

        outputJson = mapper.writeValueAsString(foundList);

        //Mock call to service layer...
        when(storeServiceLayer.findGameByTitle("Halo")).thenReturn(foundList);

        //Act & Assert
        this.mockMvc.perform(get("/games?title=Halo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetGamesByEsrbRating() throws Exception{
        //Object to JSON in String
        String outputJson = null;

        //Arrange
        Game savedGame1 = new Game();
        savedGame1.setTitle("Halo");
        savedGame1.setEsrbRating("E10");
        savedGame1.setDescription("Puzzles and Math");
        savedGame1.setPrice(new BigDecimal("23.99"));
        savedGame1.setStudio("Xbox Game Studios");
        savedGame1.setQuantity(5);
        savedGame1.setId(56);

        Game savedGame2 = new Game();
        savedGame2.setTitle("Halo I");
        savedGame2.setEsrbRating("E10");
        savedGame2.setDescription("Puzzles and Math");
        savedGame2.setPrice(new BigDecimal("23.99"));
        savedGame2.setStudio("Xbox Game Studios");
        savedGame2.setQuantity(5);
        savedGame2.setId(51);

        Game savedGame3 = new Game();
        savedGame3.setTitle("Halo IV");
        savedGame3.setEsrbRating("E18");
        savedGame3.setDescription("Puzzles and Math");
        savedGame3.setPrice(new BigDecimal("23.99"));
        savedGame3.setStudio("Xbox Game Studios");
        savedGame3.setQuantity(5);
        savedGame3.setId(77);

        List<Game> foundList = new ArrayList();
        foundList.add(savedGame1);
        foundList.add(savedGame2);

        outputJson = mapper.writeValueAsString(foundList);

        //Mock call to service layer...
        when(storeServiceLayer.findGameByESRB("10")).thenReturn(foundList);

        //Act & Assert
        this.mockMvc.perform(get("/games?esrbrating=10"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldGetGamesByStudio() throws Exception {
        //Object to JSON in String
        String outputJson = null;

        //Arrange
        Game savedGame1 = new Game();
        savedGame1.setTitle("Halo");
        savedGame1.setEsrbRating("E10+");
        savedGame1.setDescription("Puzzles and Math");
        savedGame1.setPrice(new BigDecimal("23.99"));
        savedGame1.setStudio("A&E");
        savedGame1.setQuantity(5);
        savedGame1.setId(56);

        Game savedGame2 = new Game();
        savedGame2.setTitle("Halo I");
        savedGame2.setEsrbRating("E10+");
        savedGame2.setDescription("Puzzles and Math");
        savedGame2.setPrice(new BigDecimal("23.99"));
        savedGame2.setStudio("Xbox Game Studios");
        savedGame2.setQuantity(5);
        savedGame2.setId(51);

        Game savedGame3 = new Game();
        savedGame3.setTitle("Halo IV");
        savedGame3.setEsrbRating("E18+");
        savedGame3.setDescription("Puzzles and Math");
        savedGame3.setPrice(new BigDecimal("23.99"));
        savedGame3.setStudio("A&E");
        savedGame3.setQuantity(5);
        savedGame3.setId(77);

        List<Game> foundList = new ArrayList();
        foundList.add(savedGame1);
        foundList.add(savedGame3);

        outputJson = mapper.writeValueAsString(foundList);

        //Mock call to service layer...
        when(storeServiceLayer.getGameByStudio("A&E")).thenReturn(foundList);

        //Act & Assert
        this.mockMvc.perform(get("/games?studio=A&E"))
                .andDo(print())
                .andExpect(status().isOk());


        when(storeServiceLayer.getGameByStudio("not there")).thenReturn(null);
    }


    @Test
    public void shouldGetAllGames() throws Exception{
        //Object to JSON in String
        String outputJson = null;

        //Arrange
        Game savedGame1 = new Game();
        savedGame1.setTitle("Halo");
        savedGame1.setEsrbRating("E10+");
        savedGame1.setDescription("Puzzles and Math");
        savedGame1.setPrice(new BigDecimal("23.99"));
        savedGame1.setStudio("A&E");
        savedGame1.setQuantity(5);
        savedGame1.setId(56);

        Game savedGame2 = new Game();
        savedGame2.setTitle("Halo I");
        savedGame2.setEsrbRating("E10+");
        savedGame2.setDescription("Puzzles and Math");
        savedGame2.setPrice(new BigDecimal("23.99"));
        savedGame2.setStudio("Xbox Game Studios");
        savedGame2.setQuantity(5);
        savedGame2.setId(51);

        Game savedGame3 = new Game();
        savedGame3.setTitle("Halo IV");
        savedGame3.setEsrbRating("E18+");
        savedGame3.setDescription("Puzzles and Math");
        savedGame3.setPrice(new BigDecimal("23.99"));
        savedGame3.setStudio("A&E");
        savedGame3.setQuantity(5);
        savedGame3.setId(77);

        List<Game> foundList = new ArrayList();
        foundList.add(savedGame1);
        foundList.add(savedGame3);

        outputJson = mapper.writeValueAsString(foundList);

        //Mock call to service layer...
        when(storeServiceLayer.findAllGames()).thenReturn(foundList);

        //Act & Assert
        this.mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(status().isOk());



    }

    @Test
    public void shouldFailCreateGameWithInvalidData() throws Exception {

        //perform the call, pass argutments (path variables & requestBody), use objectMapper to convert objects
        // from/to JSON format.

        //Arrange
        //title...
        Game inGame = new Game();
        inGame.setTitle("");
        inGame.setEsrbRating("E10+");
        inGame.setDescription("Puzzles and Math");
        inGame.setStudio("Xbox Game Studios");
        inGame.setQuantity(5);

        //So we are mocking (not executing the service layer) since we are testing the controller here.
        // Remember: we are testing the code of the CONTROLLER methods.
//        when(this.storeServiceLayer.createGame(inGame)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/games")
                                .content(mapper.writeValueAsString(inGame)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the game below.
                .andExpect(status().is4xxClientError()); //Expected response status code.
        ;

            }

    @Test
    public void shouldFailUpdateGameWithInvalidData() throws Exception {

        Game inGame = new Game();
        inGame.setTitle("");
        inGame.setEsrbRating("E10+");
        inGame.setDescription("Puzzles and Math");
        inGame.setPrice(new BigDecimal("23.99"));
        inGame.setStudio("Xbox Game Studios");
        inGame.setQuantity(5);
        inGame.setId(77);



        when(this.storeServiceLayer.createGame(inGame)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/games")
                                .content(mapper.writeValueAsString(inGame)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the game below.
                .andExpect(status().is4xxClientError()); //Expected response status code.
        ;

    }

    @Test
    public void shouldFailFindGamesWithInvalidData() throws Exception {


        Game inGame = new Game();
        inGame.setTitle("something");
        inGame.setEsrbRating("E10+");
        inGame.setDescription("Puzzles and Math");
        inGame.setPrice(new BigDecimal("23.99"));
        inGame.setStudio("Xbox Game Studios");
        inGame.setQuantity(5);
        inGame.setId(77);


        when(this.storeServiceLayer.findById(77)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/games/77") //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the game below.
                .andExpect(status().is4xxClientError()); //Expected response status code.

        String badValue="bad value";
        //So we are mocking (not executing the service layer) since we are testing the controller here.
        // Remember: we are testing the code of the CONTROLLER methods.
        when(this.storeServiceLayer.getGameByStudio(badValue)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/games/studio/{badValue}",badValue) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the game below.
                .andExpect(status().is4xxClientError()); //Expected response status code.

        when(this.storeServiceLayer.findGameByESRB(badValue)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/games/esrbrating/{badValue}",badValue) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the game below.
                .andExpect(status().is4xxClientError()); //Expected response status code.


        when(this.storeServiceLayer.findGameByTitle(badValue)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/games/title/{badValue}",badValue) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the game below.
                .andExpect(status().is4xxClientError()); //Expected response status code.
    }
}