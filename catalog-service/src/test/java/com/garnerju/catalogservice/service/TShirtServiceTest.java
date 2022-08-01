package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.models.TShirt;
import com.garnerju.catalogservice.repository.TShirtRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TShirtServiceTest {
    
    TShirtService tshirtService;
    TShirtRepository tshirtRepository;

    @Before
    public void setUp() throws Exception {
        setUpTShirtRepositoryMock();

        tshirtService = new TShirtService(tshirtRepository);
        TShirt tshirt2 = new TShirt();

        tshirt2.setId(1);
        tshirt2.setSize("medium");
        tshirt2.setColor("Red");
        tshirt2.setDescription("Good quality");
        tshirt2.setPrice(new BigDecimal(25.66));
        tshirt2.setQuantity(4);

        System.out.println("TShirtRepository.save(tshirt) will return " + tshirtRepository.save(tshirt2));
        System.out.println("TShirtRepository.findById(tshirt) will return " + tshirtRepository.findById(Long.valueOf(1)));
        System.out.println("TShirtRepository.findByAll(tshirt) will return " + tshirtRepository.findAll());
    }

    @Test
    public void justRunTheSetUp() {
        System.out.println("Placeholder");
    }

    private void setUpTShirtRepositoryMock() {
        tshirtRepository = mock(TShirtRepository.class);
        TShirt tshirt = new TShirt();

        tshirt.setId(1);
        tshirt.setSize("medium");
        tshirt.setColor("Red");
        tshirt.setDescription("Good quality");
        tshirt.setPrice(new BigDecimal(25.66));
        tshirt.setQuantity(4);

        TShirt tshirt2 = new TShirt();
        tshirt2.setId(1);
        tshirt2.setSize("medium");
        tshirt2.setColor("Red");
        tshirt2.setDescription("Good quality");
        tshirt2.setPrice(new BigDecimal(25.66));
        tshirt2.setQuantity(4);

        List<TShirt> inList = new ArrayList<>();
        inList.add(tshirt);

        doReturn(tshirt).when(tshirtRepository).save(tshirt2);
        doReturn(Optional.of(tshirt)).when(tshirtRepository).findById(Long.valueOf(1));
        doReturn(inList).when(tshirtRepository).findAll();

    }

}