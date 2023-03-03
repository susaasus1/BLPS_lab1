package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.request.AddCuisineRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.Ingredients;
import com.example.blps_lab1.model.NationalCuisine;
import com.example.blps_lab1.repository.NationalCuisineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NationalCuisineService {
    private final NationalCuisineRepository nationalCuisineRepository;


    public NationalCuisineService(NationalCuisineRepository nationalCuisineRepository) {
        this.nationalCuisineRepository = nationalCuisineRepository;
    }

    public NationalCuisine findNationalCuisineByName(String cuisine) throws CuisineNotFoundException {
        return nationalCuisineRepository.findNationalCuisineByCuisine(cuisine).orElseThrow(() -> new CuisineNotFoundException("Нац. кухня " + cuisine + " не существует!"));
    }

    public void saveCuisine(String nationalCuisine) throws CuisineAlreadyExistException {
        NationalCuisine cuisine = new NationalCuisine(nationalCuisine);
        if (nationalCuisineRepository.existsNationalCuisineByCuisine(nationalCuisine))
            throw new CuisineAlreadyExistException("Национальная кухня " + nationalCuisine + " уже есть в базе данных!");
        nationalCuisineRepository.save(cuisine);
    }

    public void deleteCuisine(Long cuisineId) throws CuisineNotFoundException {
        if (!nationalCuisineRepository.existsById(cuisineId))
            throw new CuisineNotFoundException("Ингредиент с id=" + cuisineId + " не существует!");
        nationalCuisineRepository.deleteById(cuisineId);
    }

    public void updateCuisine(Long cuisineId, String cuisine) throws CuisineNotFoundException {
        NationalCuisine nationalCuisine = nationalCuisineRepository.findNationalCuisineById(cuisineId).orElseThrow(() -> new CuisineNotFoundException("Кухни с id=" + cuisineId + " не существует!"));
        nationalCuisine.setCuisine(cuisine);
        nationalCuisineRepository.save(nationalCuisine);
    }

    public NationalCuisine getCuisine(Long cuisineId) throws CuisineNotFoundException {
        NationalCuisine nationalCuisine = nationalCuisineRepository.findNationalCuisineById(cuisineId).orElseThrow(() -> new CuisineNotFoundException("Кухни с id=" + cuisineId + " не существует!"));
        return nationalCuisine;
    }

    public Page<NationalCuisine> getCuisinesPage(int pageNum, int pageSize) throws IllegalPageParametersException, ResourceNotFoundException {
        if (pageNum < 1 || pageSize < 1)
            throw new IllegalPageParametersException("Номер страницы и размер страницы должны быть больше 1!");
        Pageable pageRequest = createPageRequest(pageNum - 1, pageSize);
        Page<NationalCuisine> cuisines = nationalCuisineRepository.findAll(pageRequest);
        if (cuisines.getTotalPages() < pageNum)
            throw new ResourceNotFoundException("На указанной странице не найдено записей!");
        return cuisines;
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize);
    }
}
