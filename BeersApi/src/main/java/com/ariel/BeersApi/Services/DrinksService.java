package com.ariel.BeersApi.Services;

import com.ariel.BeersApi.Dto.DrinksResponse;
import com.ariel.BeersApi.Dto.PageDto;
import com.ariel.BeersApi.Repositories.IDrinkTypesRepository;
import com.ariel.BeersApi.Repositories.IDrinksRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DrinksService {
    private final IDrinksRepository drinksRepository;
    private final IDrinkTypesRepository drinkTypesRepository;

    @Autowired
    public DrinksService(IDrinksRepository dr, IDrinkTypesRepository dtr) {
        drinksRepository = dr;
        drinkTypesRepository = dtr;
    }

    public PageDto<DrinksResponse> getAll(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> drinkTypeId) {

    }
}
