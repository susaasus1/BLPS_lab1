package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.request.AddTasteRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.Dish;
import com.example.blps_lab1.model.Tastes;
import com.example.blps_lab1.repository.TastesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void saveTaste(String tastes) throws TasteAlreadyExistException {
        Tastes taste = new Tastes(tastes);
        if (tastesRepository.existsTastesByTaste(tastes))
            throw new TasteAlreadyExistException("Вкус " + tastes + " уже есть в базе данных!");
        tastesRepository.save(taste);
    }

    public void deleteTaste(Long tasteId) throws TasteNotFoundException {
        if (!tastesRepository.existsById(tasteId))
            throw new TasteNotFoundException("Вкус с id=" + tasteId + " уже есть в базе данных!");
        tastesRepository.deleteById(tasteId);
    }

    public void updateTaste(Long tasteId, String tastes) throws TasteNotFoundException {
        Tastes taste = tastesRepository.findTastesById(tasteId).orElseThrow(() -> new TasteNotFoundException("Вкус с id=" + tasteId + " не существует!"));
        taste.setTaste(tastes);
        tastesRepository.save(taste);
    }

    public Tastes getTaste(Long tasteId) throws TasteNotFoundException {
        Tastes taste = tastesRepository.findTastesById(tasteId).orElseThrow(() -> new TasteNotFoundException("Вкус с id=" + tasteId + " не существует!"));
        return taste;
    }

    public Page<Tastes> getAllTaste(int pageNum, int pageSize) throws ResourceNotFoundException, IllegalPageParametersException {
        if (pageNum < 1 || pageSize < 1)
            throw new IllegalPageParametersException("Номер страницы и размер страницы должны быть больше 1!");
        Pageable pageRequest = createPageRequest(pageNum - 1, pageSize);
        Page<Tastes> tastes = tastesRepository.findAll(pageRequest);
        if (tastes.getTotalPages() < pageNum)
            throw new ResourceNotFoundException("На указанной странице не найдено записей!");
        return tastes;
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize);
    }
}
