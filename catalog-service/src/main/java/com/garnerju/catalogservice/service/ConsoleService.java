package com.garnerju.catalogservice.service;


import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ConsoleService {

    @Autowired
    ConsoleRepository consoleRepository;

    public ConsoleService(ConsoleRepository consoleRepository) {
    }


    public List<Console> findAllConsoles() {
        List<Console> consoleList = consoleRepository.findAll();

        if (consoleList == null || consoleList.isEmpty()) {
            throw new RuntimeException("No consoles were found.");
        } else {
            return consoleList;
        }
    }

    public List<Console> findConsoleByManufacturer(String manufacturer) {

        List<Console> consoleList = consoleRepository.findAllByManufacturer(manufacturer);

        if (consoleList == null || consoleList.isEmpty()) {
            throw new RuntimeException("No consoles by that manufacturer were found.");
        } else {
            return consoleList;
        }
    }

    public Console findById(Long id) {
        Optional<Console> existingConsole = consoleRepository.findById(id);
        if (existingConsole.isPresent()) {
            return existingConsole.get();
        }
        else throw new RuntimeException("Console with that ID does not exist");
    }



    public Console createConsole(Console newConsole) {  return consoleRepository.save(newConsole);  }

    public Console updateConsole(Console console) {

        Optional<Console> existingConsole = consoleRepository.findById(console.getId());
        if (existingConsole.isPresent()) {
            console.setId(existingConsole.get().getId());
            return consoleRepository.save(console);
        }
        else throw new RuntimeException("Console with that ID does not exist");
        }

    public void deleteConsole(long id) { consoleRepository.deleteById(id); }


}
