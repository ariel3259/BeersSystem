package com.ariel.BeersApi.Services;

import com.ariel.BeersApi.Dto.DrinkTypesRequest;
import com.ariel.BeersApi.Dto.DrinkTypesResponse;
import com.ariel.BeersApi.Dto.PageDto;
import com.ariel.BeersApi.Dto.ServiceResponse;
import com.ariel.BeersApi.Model.DrinkTypes;
import com.ariel.BeersApi.Repositories.IDrinkTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class DrinkTypesService {
    private final IDrinkTypesRepository drinkTypesRepository;

    @Autowired
    public DrinkTypesService(IDrinkTypesRepository dtr) {
        drinkTypesRepository = dtr;
    }

    public PageDto<DrinkTypesResponse> getAll(Optional<Integer> page, Optional<Integer> limit) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(10));
        Page<DrinkTypes> pageDrinkTypes = drinkTypesRepository.getAllByStatusTrue(pageable);
        System.out.println("" + pageDrinkTypes.getNumberOfElements());
        List<DrinkTypesResponse> drinkTypesResponse = pageDrinkTypes.get()
                .map((x) -> new DrinkTypesResponse(x.getId(), x.getDescription()))
                .toList();
        return new PageDto<>(drinkTypesResponse, pageDrinkTypes.getTotalElements());
    }

    public ServiceResponse<DrinkTypesResponse> create(DrinkTypesRequest request) {
        Optional<DrinkTypes> drinkTypeOptional = drinkTypesRepository.findByDescription(request.getDescription());
        if (drinkTypeOptional.isPresent()) {
            DrinkTypes drinkType = drinkTypeOptional.get();
            if (drinkType.isStatus())
                return ServiceResponse.error("The type already exits");
            else {
                drinkType.setStatus(true);
                drinkTypesRepository.save(drinkType);
                return ServiceResponse.success(new DrinkTypesResponse(drinkType.getId(), drinkType.getDescription()));
            }
        }
        DrinkTypes drinkType = new DrinkTypes(request.getDescription());
        DrinkTypes response = drinkTypesRepository.save(drinkType);
        return ServiceResponse.success(new DrinkTypesResponse(response.getId(), response.getDescription()));
    }

    public void delete(int id) {
        drinkTypesRepository.delete(id);
    }
}
