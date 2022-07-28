package com.garnerju.catalogservice.controllers;

import com.garnerju.catalogservice.models.TShirt;
import com.garnerju.catalogservice.service.TShirtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class TShirtController {
    @Autowired
    TShirtService tShirtService;

    @GetMapping("/tShirts")
    public List<TShirt> getTShirt() { return tShirtService.findAllTShirts(); }

    @GetMapping("/tShirts/{id}")
    public TShirt getTShirtById(long id) {return tShirtService.findById(id);}

    @PostMapping("/tShirts")
    public TShirt createTShirt(@RequestBody TShirt tShirt) {return tShirtService.createTShirt(tShirt);}

    @PutMapping("/tShirts")
    public TShirt updateTShirt(@RequestBody TShirt tShirt) {return tShirtService.updateTShirt(tShirt);}

    @DeleteMapping("/tShirts/{id}")
    public void deleteTShirt(@PathVariable long id) {tShirtService.deleteTShirt(id);}
}
