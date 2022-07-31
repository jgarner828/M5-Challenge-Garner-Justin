package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.errors.NotFoundException;
import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ConsoleService {

    @Autowired
    ConsoleRepository consoleRepository;


    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }


    public Console findById(Long id) {
        return consoleRepository.findById(id).orElseThrow(NotFoundException::new);
    }


    public Console createConsole(@Valid Console newConsole) {   return consoleRepository.save(newConsole);   }

    public Console updateConsole(@RequestBody @Valid Console console) {
        Optional<Console> existingConsole = consoleRepository.findById(console.getId());
        if (existingConsole.isPresent()) {
            console.setId(existingConsole.get().getId());
            return consoleRepository.save(console);
        }
        else throw new NotFoundException();
        }

    public void deleteConsole(long id) {
        consoleRepository.deleteById(id);
    }

    public List<Console> getConsoleByManufacturer(@PathVariable("manufacturer") String manufacturer) {return consoleRepository.findAllByManufacturer(manufacturer);}

    public List<Console> findAllConsolesByManufacturer(String manufacturer) {
        return consoleRepository.findAllByManufacturer(manufacturer);
    }
}
