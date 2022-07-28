package com.garnerju.catalogservice.controllers;

import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class ConsoleController {

    @Autowired
    ConsoleService consoleService;

    @GetMapping("/consoles")
    public List<Console> getConsole() { return consoleService.findAllConsoles(); }

    @GetMapping("/consoles/{id}")
    public Console getConsoleById(long id) {return consoleService.findById(id);}

    @PostMapping("/consoles")
    public Console createConsole(@RequestBody Console console) {return consoleService.createConsole(console);}

    @PutMapping("/consoles")
    public Console updateConsole(@RequestBody Console console) {return consoleService.updateConsole(console);}

    @DeleteMapping("/consoles/{id}")
    public void deleteConsole(@PathVariable long id) {consoleService.deleteConsole(id);}
}
