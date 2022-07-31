package com.garnerju.catalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garnerju.catalogservice.controllers.TShirtController;
import com.garnerju.catalogservice.models.TShirt;

import com.garnerju.catalogservice.repository.TShirtRepository;
import com.garnerju.catalogservice.service.TShirtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TShirtController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class TShirtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TShirtService shirtService;
    
    @MockBean
    TShirtRepository shirtRepository;

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    public void ShouldGetTShirts() throws Exception {

        List<TShirt> expectedTShirt = new ArrayList<>(Arrays.asList(
                new TShirt(1,"md","White","md white shirt",new BigDecimal("10.99"),5)));
        
        
        when(shirtService.findAllTShirts()).thenReturn(expectedTShirt);
        String expectedJson = mapper.writeValueAsString(expectedTShirt);

        mockMvc.perform(get("/tShirts")) //Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void ShouldGetTShirtsByColor() throws Exception {

        List<TShirt> expectedTShirt = new ArrayList<>(Arrays.asList(
                new TShirt(1,"md","White","md white shirt",new BigDecimal("10.99"),5)));

        when(shirtRepository.findAllByColor("white")).thenReturn(expectedTShirt);
        String expectedJson = mapper.writeValueAsString(expectedTShirt);

        mockMvc.perform(get("/tShirts?color=white")) //Act
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldGetTShirtsBySize() throws Exception {

        List<TShirt> expectedTShirt = new ArrayList<>(Arrays.asList(
                new TShirt(1,"md","White","md white shirt",new BigDecimal("10.99"),5)));
        when(shirtRepository.findAllBySize("md")).thenReturn(expectedTShirt);

        String expectedJson = mapper.writeValueAsString(expectedTShirt);

        mockMvc.perform(get("/tShirts?size=md")) //Act
                .andDo(print())
                .andExpect(status().isOk());
    }

//    TODO NEED TO TROUBLESHOOT WHY IT IS RETURNING IMPROPERLY.
//    @Test
//    public void ShouldGetTShirtsById() throws Exception {
//
//        TShirt newShirt = new TShirt(Long.valueOf(1),"small", "white", "shirt", BigDecimal.valueOf(10.00), 10);
//
//        when(shirtService.findById(newShirt.getId())).thenReturn(newShirt);
//
//        mockMvc.perform( MockMvcRequestBuilders
//                        .get("/tShirts/{id}",newShirt.getId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
//                .andExpect(status().isOk());
//    }

    @Test
    public void shouldCreateTShirt() throws Exception{

        TShirt inputBody = new TShirt("md","White","md white shirt",new BigDecimal("10.99"),5);
        TShirt expectedTShirt = new TShirt(1,"md","White","md white shirt",new BigDecimal("10.99"),5);

        when(shirtService.createTShirt(inputBody)).thenReturn(expectedTShirt);

        String expectedJson = mapper.writeValueAsString(expectedTShirt);
        String inputJson = mapper.writeValueAsString(inputBody);


        mockMvc.perform(post("/tShirts")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated());
    }

    @Test
    public void ShouldReturn422UProcessableWhenSearchingTShirtByIdWithAwrongEntryType() throws Exception{
        mockMvc.perform(get("/tShirts/rt"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void ShouldReturn404NotFoundWhenMisSpelledUrlProvided() throws Exception{

        mockMvc.perform(get("/tShirts90/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}