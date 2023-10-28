package com.ariel.BeersApi.Services;

import com.ariel.BeersApi.Dto.DrinkTypesResponse;
import com.ariel.BeersApi.Dto.DrinksResponse;
import com.ariel.BeersApi.Dto.PageDto;
import com.ariel.BeersApi.Model.DrinkTypes;
import com.ariel.BeersApi.Model.Drinks;
import com.ariel.BeersApi.Repositories.IDrinkTypesRepository;
import com.ariel.BeersApi.Repositories.IDrinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        Page<Drinks> records;
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(10));
        if (drinkTypeId.isPresent()) records = drinksRepository.getAllByDrinkTypesIdAndStatusTrue(pageable, drinkTypeId.get());
        else records = drinksRepository.getAllByStatusTrue(pageable);
        List<DrinksResponse> drinksResponse = records.get()
                .map((x) -> {
                     DrinkTypes drinkType = x.getDrinkType();
                     return new DrinksResponse(x.getId(), x.getName(), x.getAlcoholRate(), x.getPrice(), new DrinkTypesResponse(drinkType.getId(), drinkType.getDescription()));
                })
                .toList();
        return new PageDto<>(drinksResponse, records.getTotalElements());
    }

    public DrinksResponse getByDrinksId(int drinksId){
        Drinks drink = drinksRepository.getReferenceByIdAndStatusTrue(drinksId);
        if (drink == null) return null;
        DrinkTypes drinkType = drink.getDrinkType();
        return new DrinksResponse(drink.getId(), drink.getName(), drink.getAlcoholRate(), drink.getPrice(), new DrinkTypesResponse(drinkType.getId(), drinkType.getDescription()));
    }
}
