package com.garnerju.catalogservice.controllers;


import com.garnerju.catalogservice.errors.NotFoundException;
import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.service.ConsoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
//@AutoConfigureMockMvc(addFilters = false)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class ConsoleControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsoleService storeServiceLayer;


    @Autowired
    private ObjectMapper mapper;



    @Test
    public void shouldReturnNewConsoleOnPostRequest() throws Exception {

        Console inConsole =
                new Console("64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);
        Console outConsole =
                new Console(Long.valueOf(1), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);


        when(this.storeServiceLayer.createConsole(inConsole)).thenReturn(outConsole);

        inConsole.setId(outConsole.getId());

        mockMvc.perform(
                        post("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(outConsole))); //matches the output of the Controller with the mock output.
    }

    @Test
    public void shouldReturnConsoleById() throws Exception{


        Console outConsole =
                new Console(Long.valueOf(1), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);


        when(storeServiceLayer.findById(Long.valueOf(1))).thenReturn(outConsole);



        mockMvc.perform( MockMvcRequestBuilders
                        .get("/consoles/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn204StatusWithGoodUpdate() throws Exception {


        Console inConsole =
                new Console(Long.valueOf(1), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }


    @Test
    public void shouldReturn422StatusWithBadIdUpdateRequest() throws Exception {
        Console inConsole =
                new Console(Long.valueOf(10), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);



        doThrow(new NotFoundException("Console not found. Unable to update")).when(storeServiceLayer).updateConsole(inConsole);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteConsoleReturnNoContent() throws Exception{

        doNothing().when(storeServiceLayer).deleteConsole(15);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/consoles/{id}",15))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnConsoleByManufacturer() throws Exception {

        List<Console> consoleList = new ArrayList<>();



        Console console1 =
                new Console(Long.valueOf(1), "64", "Sony", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);


        when(storeServiceLayer.getConsoleByManufacturer("Sony")).thenReturn(consoleList);

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/consoles?manufacturer=Sony")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(consoleList)));
    }

    @Test
    public void shouldReturnAllConsoles() throws Exception {

        List<Console> consoleList = new ArrayList<>();

        consoleList.add(new Console(Long.valueOf(2),"64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10));
        consoleList.add(new Console(Long.valueOf(1), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10));



        when(storeServiceLayer.findAllConsoles()).thenReturn(consoleList);

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/consoles")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(consoleList)));

        when(storeServiceLayer.findAllConsoles()).thenReturn(null);

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/consoles")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    public void shouldFailCreateConsoleWithInvalidQuantity() throws Exception {


        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setId(Long.valueOf(1));
        inConsole.setQuantity(0);


        when(this.storeServiceLayer.createConsole(inConsole)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());


        inConsole = new Console();
        inConsole.setId(Long.valueOf(1));
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(50001);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldFailCreateConsoleWithInvalidPrice() throws Exception {


        //Mock "in"coming Console  with null price
        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(2);
        inConsole.setId(Long.valueOf(1));
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(null);

        when(this.storeServiceLayer.createConsole(inConsole)).thenReturn(null);

        //ResultActions x = mockMvc.perform(
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;

        //Mock "in"coming Console  with no price
        inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(2);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setId(Long.valueOf(1));

        //ResultActions x = mockMvc.perform(
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;

    }

    @Test
    public void shouldFailCreateConsoleInvalidManufacturer() throws Exception {



        //Mock "in"coming Console  with 0 quantity
        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(1);
        inConsole.setManufacturer(null);
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("10.99"));
        inConsole.setId(Long.valueOf(1));

        //the following mocks the service layer's method "createConsole"
        //So we are mocking (not executing the service layer) since we are testing the controller here.
        // Remember: we are testing the code of the CONTROLLER methods.
        when(this.storeServiceLayer.createConsole(inConsole)).thenReturn(null);

        //ResultActions x = mockMvc.perform(
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;
    }

    @Test
    public void shouldFailCreateConsoleInvalidModel() throws Exception {


        //Mock "in"coming Console  with 0 quantity
        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setModel("Genesis");
        inConsole.setQuantity(0);
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("10.99"));
        inConsole.setId(Long.valueOf(1));

        //the following mocks the service layer's method "createConsole"
        //So we are mocking (not executing the service layer) since we are testing the controller here.
        // Remember: we are testing the code of the CONTROLLER methods.
        when(this.storeServiceLayer.createConsole(inConsole)).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;

        //Mock "in"coming Console  with 0 quantity
        inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(0);
        inConsole.setManufacturer("Sega");
        inConsole.setModel(null);
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("10.99"));
        inConsole.setId(Long.valueOf(1));

        //ResultActions x = mockMvc.perform(
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;
    }

    @Test
    public void shouldFailUpdateConsoleInvalidModel() throws Exception {

        //perform the call, pass arguments (path variables & requestBody), use objectMapper to convert objects
        // from/to JSON format.

        //Mock "in"coming Console  with 0 quantity
        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(0);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("10.99"));
        inConsole.setId(Long.valueOf(15));


//        doNothing().when(this.storeServiceLayer).updateConsole(inConsole);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;

        //Mock "in"coming Console  with 0 quantity
        inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(0);
        inConsole.setManufacturer("Sega");
        inConsole.setModel(null);
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("10.99"));
        inConsole.setId(Long.valueOf(15));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;
    }

    @Test
    public void shouldFailUpdateConsoleInvalidQuantity() throws Exception {


        //Mock "in"coming Console  with 0 quantity
        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(0);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("199.89"));
        inConsole.setId(Long.valueOf(15));


//        doNothing().when(this.storeServiceLayer).updateConsole(inConsole);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
        ;

    }

    @Test
    public void shouldFailUpdateConsoleInvalidPrice() throws Exception {


        //Mock "in"coming Console  with null price
        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(2);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(null);
        inConsole.setId(Long.valueOf(15));


        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;

        //Mock "in"coming Console  with no price
        inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(2);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(null);
        inConsole.setId(Long.valueOf(15));


        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;

        //Mock "in"coming Console  with no price
        inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(2);
        inConsole.setManufacturer("Sega");
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(BigDecimal.valueOf(0));
        inConsole.setId(Long.valueOf(15));

        //ResultActions x = mockMvc.perform(
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;
    }

    @Test
    public void shouldFailUpdateConsoleInvalidManufacturer() throws Exception {


        Console inConsole = new Console();
        inConsole.setMemoryAmount("250GB");
        inConsole.setQuantity(2);
        inConsole.setManufacturer(null);
        inConsole.setModel("Nintendo");
        inConsole.setProcessor("AMD");
        inConsole.setPrice(new BigDecimal("10.99"));
        inConsole.setId(Long.valueOf(15));


        mockMvc.perform(
                        MockMvcRequestBuilders.put("/consoles")
                                .content(mapper.writeValueAsString(inConsole)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().is4xxClientError()) //Expected response status code.
        ;
    }

    @Test
    public void shouldFailGetConsoleWithBadId() throws Exception{
        //Mock "out"put Console...
        Console outConsole = new Console();
        outConsole.setMemoryAmount("250GB");
        outConsole.setQuantity(12);
        outConsole.setManufacturer("Sega");
        outConsole.setModel("Nintendo");
        outConsole.setProcessor("AMD");
        outConsole.setPrice(new BigDecimal("199.89"));
        outConsole.setId(Long.valueOf(15));

        //the following mocks the service layer's method "createConsole"
        //So we are mocking (not executing the service layer) since we are testing the controller here.
        // Remember: we are testing the code of the CONTROLLER methods.
        when(storeServiceLayer.findById(Long.valueOf(15))).thenReturn(null);

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/console/{id}", 16)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNotFound())
        ;

    }

    @Test
    public void shouldFailGetConsoleByManufacturerWithInvalidManufacturer() throws Exception {

        //no need to create dummy data since we are returning null anyway.
        List<Console> consoleList = new ArrayList<>();

        //the following mocks the service layer's method "createConsole"
        //So we are mocking (not executing the service layer) since we are testing the controller here.
        //Remember: we are testing the code of the CONTROLLER methods.
        when(storeServiceLayer.getConsoleByManufacturer("Sony")).thenReturn(null);

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/consoles/manufacturer/{manufacturer}", "Sony")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNotFound())
        ;
    }


}