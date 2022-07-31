package com.garnerju.invoiceservice.util.feign;

import com.garnerju.invoiceservice.model.Console;
import com.garnerju.invoiceservice.model.Game;
import com.garnerju.invoiceservice.model.TShirt;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@FeignClient(name="gamestore-catalog", configuration= FeignClientsConfiguration.class, url="http://localhost:7474")
@RefreshScope
public interface CatalogClient {

    @GetMapping("/console")
    public List<Console> getConsole();

    @GetMapping("/console/{id}")
    public Console getConsoleById(@PathVariable("id") long id);

    @PostMapping("/console")
    public Console createConsole(@RequestBody Console console);

    @PutMapping("/console")
    public Console updateConsole(@RequestBody Console console);

    @DeleteMapping("/console/{id}")
    public void deleteConsole(@PathVariable("id") long id);

    @GetMapping("/games")
    public List<Game> getGame();

    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable("id") long id);

    @PostMapping("/game")
    public Game createGame(@RequestBody Game game);

    @PutMapping("/game")
    public Game updateGame(@RequestBody Game game);

    @DeleteMapping("/game/{id}")
    public void deleteGame(@PathVariable("id") long id);

    @GetMapping("/tshirt")
    public List<TShirt> getTShirt();

    @GetMapping("/tshirt/{id}")
    public TShirt getTShirtById(@PathVariable("id") long id);

    @PostMapping("/tshirt")
    public TShirt createTShirt(@RequestBody TShirt tShirt);

    @PutMapping("/tshirt")
    public TShirt updateTShirt(@RequestBody TShirt tShirt);

    @DeleteMapping("/tshirt/{id}")
    public void deleteTShirt(@PathVariable("id") long id);
}
