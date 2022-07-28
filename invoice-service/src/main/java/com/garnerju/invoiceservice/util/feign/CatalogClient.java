package com.garnerju.invoiceservice.util.feign;

import com.garnerju.invoiceservice.model.Console;
import com.garnerju.invoiceservice.model.Game;
import com.garnerju.invoiceservice.model.TShirt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@FeignClient(name="gamestore-catalog", configuration= FeignClientsConfiguration.class)
public interface CatalogClient {

    @GetMapping("/consoles")
    public List<Console> getConsole();

    @GetMapping("/consoles/{id}")
    public Console getConsoleById(long id);

    @PostMapping("/consoles")
    public Console createConsole(@RequestBody Console console);

    @PutMapping("/consoles")
    public Console updateConsole(@RequestBody Console console);

    @DeleteMapping("/consoles/{id}")
    public void deleteConsole(@PathVariable long id);

    @GetMapping("/games")
    public List<Game> getGame();

    @GetMapping("/games/{id}")
    public Game getGameById(long id);

    @PostMapping("/games")
    public Game createGame(@RequestBody Game game);

    @PutMapping("/games")
    public Game updateGame(@RequestBody Game game);

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable long id);

    @GetMapping("/tShirts")
    public List<TShirt> getTShirt();

    @GetMapping("/tShirts/{id}")
    public TShirt getTShirtById(long id);

    @PostMapping("/tShirts")
    public TShirt createTShirt(@RequestBody TShirt tShirt);

    @PutMapping("/tShirts")
    public TShirt updateTShirt(@RequestBody TShirt tShirt);

    @DeleteMapping("/tShirts/{id}")
    public void deleteTShirt(@PathVariable long id);
}
