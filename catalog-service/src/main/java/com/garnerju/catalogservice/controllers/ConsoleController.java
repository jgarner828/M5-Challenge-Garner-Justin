package com.garnerju.catalogservice.controllers;

import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RefreshScope
public class ConsoleController {

    @Autowired
    ConsoleService consoleService;

    @GetMapping("/console")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsole(@PathParam("manufacturer") String manufacturer) {
        if(manufacturer == null) return consoleService.findAllConsoles();
        else return consoleService.findConsoleByManufacturer(manufacturer);}

    @GetMapping("/console/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsoleById(@PathVariable("id") Long id) {  return consoleService.findById(id);  }

    @PostMapping("/console")
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@RequestBody @Valid Console console) {
        if(console.getPrice() == null || console.getPrice().equals(BigDecimal.valueOf(0)))throw new RuntimeException("Must have a price");
        if(console.getQuantity() < 1) throw new RuntimeException("Must have a quantity");
        if(console.getManufacturer() == null) throw new RuntimeException("Must have a manufacturer");
        return consoleService.createConsole(console);}

    @PutMapping("/console")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Console updateConsole(@RequestBody @Valid Console console) {
        if(console.getPrice() == null || console.getPrice().equals(BigDecimal.valueOf(0)))throw new RuntimeException("Must have a price");
        if(console.getQuantity() < 1) throw new RuntimeException("Must have a quantity");
        if(console.getManufacturer() == null) throw new RuntimeException("Must have a manufacturer");
        return consoleService.updateConsole(console);}

    @DeleteMapping("/console/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable Long id) {consoleService.deleteConsole(id);}
}
