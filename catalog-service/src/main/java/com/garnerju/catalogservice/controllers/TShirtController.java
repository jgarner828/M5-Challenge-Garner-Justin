package com.garnerju.catalogservice.controllers;

import com.garnerju.catalogservice.models.TShirt;
import com.garnerju.catalogservice.service.TShirtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RefreshScope
public class TShirtController {
    @Autowired
    TShirtService tShirtService;

    @GetMapping("/tShirts")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getTShirt(@PathParam("color") String color, @PathParam("size") String size) {
        if (color == null && size == null)  return tShirtService.findAllTShirts();
        if (size == null) return tShirtService.findByColor(color);
        else return tShirtService.findBySize(size);
    }

    @GetMapping("/tShirts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TShirt getTShirtById(Long id) {return tShirtService.findById(id);}

    @PostMapping("/tShirts")
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt createTShirt(@RequestBody @Valid  TShirt tShirt) {return tShirtService.createTShirt(tShirt);}

    @PutMapping("/tShirts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TShirt updateTShirt(@RequestBody @Valid TShirt tShirt) {return tShirtService.updateTShirt(tShirt);}

    @DeleteMapping("/tShirts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable Long id) {tShirtService.deleteTShirt(id);}
}
