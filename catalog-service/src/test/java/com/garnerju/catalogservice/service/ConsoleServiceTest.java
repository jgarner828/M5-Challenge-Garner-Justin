package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.models.Console;
import com.garnerju.catalogservice.repository.ConsoleRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ConsoleServiceTest {
    ConsoleService consoleService;
    ConsoleRepository consoleRepository;

    @Before
    public void setUp() throws Exception {
        setUpConsoleRepositoryMock();
        consoleService = new ConsoleService(consoleRepository);
        Console console2 = new Console();
        console2.setId(1);
        console2.setModel("Xbox Serious S");
        console2.setManufacturer("Microsoft");
        console2.setMemoryAmount("10GB");
        console2.setProcessor("Window");
        console2.setPrice(new BigDecimal(735.00));
        console2.setQuantity(2);

        System.out.println("ConsoleRepository.save(console) will return " + consoleRepository.save(console2));
        System.out.println("ConsoleRepository.findById(console) will return " + consoleRepository.findById(Long.valueOf(1)));
        System.out.println("ConsoleRepository.findAll(console) will return " + consoleRepository.findAll());
    }

    @Test
    public void justRunTheSetUp() {
        System.out.println("Test Run: ");
    }

    private void setUpConsoleRepositoryMock() {
        consoleRepository = mock(ConsoleRepository.class);
        Console console = new Console();
        console.setId(1);
        console.setModel("Xbox Serious S");
        console.setManufacturer("Microsoft");
        console.setMemoryAmount("10GB");
        console.setProcessor("Window");
        console.setPrice(new BigDecimal(735.00));
        console.setQuantity(2);

        Console console2 = new Console();
        console2.setId(1);
        console2.setModel("Xbox Serious S");
        console2.setManufacturer("Microsoft");
        console2.setMemoryAmount("10GB");
        console2.setProcessor("Window");
        console2.setPrice(new BigDecimal(735.00));
        console2.setQuantity(2);


        List<Console> inList = new ArrayList<>();
        inList.add(console);

        doReturn(console).when(consoleRepository).save(console2);
        doReturn(Optional.of(console)).when(consoleRepository).findById(Long.valueOf(1));
        doReturn(inList).when(consoleRepository).findAll();
    }

}