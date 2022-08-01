package com.garnerju.catalogservice.service;

import com.garnerju.catalogservice.errors.NotFoundException;
import com.garnerju.catalogservice.models.Console;
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

    public TShirtService(TShirtRepository tshirtRepository) {
    }


    public List<TShirt> findAllTShirts() {
        List<TShirt> tShirtList = tShirtRepository.findAll();

        if (tShirtList == null || tShirtList.isEmpty()) {
            throw new RuntimeException("No shirts were found.");
        } else {
            return tShirtList;
        }
    }

    public TShirt findById(long id) {  return tShirtRepository.findById(id).orElse(null);  }

    public TShirt createTShirt(TShirt newTShirt) {
        return tShirtRepository.save(newTShirt);
    }

    public TShirt updateTShirt(TShirt tShirt) {      //Validate incoming Game Data in the view model
        if (tShirt == null) {
            throw new RuntimeException("No shirt data is passed! shirt object is null!");
        }
        //make sure the game exists. and if not, throw exception...
        else if (this.findById(tShirt.getId()) == null) {
            throw new RuntimeException("No such shirt to update.");
        }
        return tShirtRepository.save(tShirt);
    }

    public void deleteTShirt(long id) {  tShirtRepository.deleteById(id);   }

    public List<TShirt> findByColor(String color) {

        List<TShirt> tShirtList = tShirtRepository.findAllByColor(color);

        if (tShirtList == null || tShirtList.isEmpty()) {
            throw new RuntimeException("No shirts that color were found.");
        } else {
            return tShirtList;
        }
    }

    public List<TShirt> findBySize(String size) {

        List<TShirt> tShirtList = tShirtRepository.findAllBySize(size);

        if (tShirtList == null || tShirtList.isEmpty()) {
            throw new RuntimeException("No shirts that size were found.");
        } else {
            return tShirtList;
        }
    }
}
