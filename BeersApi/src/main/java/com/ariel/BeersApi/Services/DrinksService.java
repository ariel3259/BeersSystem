package com.ariel.BeersApi.Services;

import com.ariel.BeersApi.Dto.*;
import com.ariel.BeersApi.Model.DrinkTypes;
import com.ariel.BeersApi.Model.Drinks;
import com.ariel.BeersApi.Repositories.IDrinkTypesRepository;
import com.ariel.BeersApi.Repositories.IDrinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        if (drinkTypeId.isPresent()) records = drinksRepository.getAllByDrinkTypeAndStatusTrue(drinkTypeId.get(), pageable);
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
    public ServiceResponse<DrinksResponse> save(DrinksRequest dto) {
        DrinkTypes drinkType = drinkTypesRepository.getReferenceByIdAndStatusTrue(dto.getDrinkTypesId());
        if (drinkType == null)
            return ServiceResponse.error("The drink type doesn't exits");
        Drinks drinkToCreate = new Drinks(dto.getName(), dto.getAlcoholRate(), dto.getPrice(), drinkType);
        Drinks drink =drinksRepository.save(drinkToCreate);
        DrinksResponse response = new DrinksResponse(drink.getId(), drink.getName(), drink.getAlcoholRate(), drink.getPrice(), new DrinkTypesResponse(drinkType.getId(), drinkType.getDescription()));
        return ServiceResponse.success(response);
    }

    public ServiceResponse<DrinksResponse> update(DrinksUpdateRequest dto, int drinkId) {
        Drinks drink = drinksRepository.getReferenceByIdAndStatusTrue(drinkId);
        if (drink == null)
            return ServiceResponse.error("The drink does not exits");
        drink.setName(dto.getName().orElse(drink.getName()));
        drink.setAlcoholRate(dto.getAlcoholRate().orElse(drink.getAlcoholRate()));
        drink.setPrice(dto.getPrice().orElse(drink.getPrice()));
        DrinkTypes drinkTypeOld = drink.getDrinkType();
        if (dto.getDrinkTypeId().isPresent() && dto.getDrinkTypeId().get() != drinkTypeOld.getId()) {
            DrinkTypes drinkTypeNew = drinkTypesRepository.getReferenceByIdAndStatusTrue(dto.getDrinkTypeId().get());
            if (drinkTypeNew == null)
                return ServiceResponse.error("The drink type does not exits");
            drink.setDrinkType(drinkTypeNew);
        }
        Drinks drinkUpdated = drinksRepository.save(drink);
        DrinkTypes drinkType = drinkUpdated.getDrinkType();
        DrinksResponse drinksResponse = new DrinksResponse(
                drinkUpdated.getId(),
                drinkUpdated.getName(),
                drinkUpdated.getAlcoholRate(),
                drinkUpdated.getPrice(),
                new DrinkTypesResponse(
                        drinkType.getId(),
                        drinkType.getDescription()
                ));
        return ServiceResponse.success(drinksResponse);
    }

    public void delete(int drinksId) {
        drinksRepository.delete(drinksId);
    }
}
