package com.example.blps_lab1.service;

import com.example.blps_lab1.exception.TasteNotFoundException;
import com.example.blps_lab1.model.Tastes;
import com.example.blps_lab1.repository.TastesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TastesService {
    private final TastesRepository tastesRepository;


    public TastesService(TastesRepository tastesRepository) {
        this.tastesRepository = tastesRepository;
    }

    public Tastes findTasteByTasteName(String taste) throws TasteNotFoundException {
        return tastesRepository.findTastesByTaste(taste).orElseThrow(() -> new TasteNotFoundException("Вкус " + taste + " не найден"));
    }

    public List<Tastes> findAllTastesByTasteNames(List<String> tasteNames) throws TasteNotFoundException {
        List<Tastes> tastesList = new ArrayList<>();
        for (String taste_name : tasteNames) {
            Tastes taste = findTasteByTasteName(taste_name);
            tastesList.add(taste);
        }
        return tastesList;
    }


}
