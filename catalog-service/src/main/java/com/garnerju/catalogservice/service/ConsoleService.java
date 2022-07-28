package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ConsoleService {

    @Autowired
    ConsoleRepository consoleRepository;


    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }


    public Console findById(long id) {
        return consoleRepository.findById(id).orElse(null);
    }


    public Console createConsole(Console newConsole) {
        return consoleRepository.save(newConsole);
    }

    public Console updateConsole(@RequestBody Console console) {return consoleRepository.save(console);}

    public void deleteConsole(long id) {
        consoleRepository.deleteById(id);
    }
}
