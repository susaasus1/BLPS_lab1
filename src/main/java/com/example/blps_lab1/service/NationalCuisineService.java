package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.request.AddCuisineRequest;
import com.example.blps_lab1.exception.CuisineAlreadyExistException;
import com.example.blps_lab1.exception.CuisineNotFoundException;
import com.example.blps_lab1.model.NationalCuisine;
import com.example.blps_lab1.repository.NationalCuisineRepository;
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

    public void saveCuisine(AddCuisineRequest addCuisineRequest) throws CuisineAlreadyExistException {
        NationalCuisine cuisine = new NationalCuisine(addCuisineRequest.getCuisine());
        if (nationalCuisineRepository.existsNationalCuisineByCuisine(addCuisineRequest.getCuisine()))
            throw new CuisineAlreadyExistException("Национальная кухня " + addCuisineRequest.getCuisine() + " уже есть в базе данных!");
        nationalCuisineRepository.save(cuisine);
    }
}
