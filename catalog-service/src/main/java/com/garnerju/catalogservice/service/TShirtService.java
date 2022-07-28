package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.models.TShirt;
import com.garnerju.catalogservice.repository.TShirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TShirtService {
    @Autowired
    TShirtRepository tShirtRepository;


    public List<TShirt> findAllTShirts() {
        return tShirtRepository.findAll();
    }


    public TShirt findById(long id) {
        return tShirtRepository.findById(id).orElse(null);
    }


    public TShirt createTShirt(TShirt newTShirt) {
        return tShirtRepository.save(newTShirt);
    }

    public TShirt updateTShirt(@RequestBody TShirt tShirt) {return tShirtRepository.save(tShirt);}

    public void deleteTShirt(long id) {
        tShirtRepository.deleteById(id);
    }
}
