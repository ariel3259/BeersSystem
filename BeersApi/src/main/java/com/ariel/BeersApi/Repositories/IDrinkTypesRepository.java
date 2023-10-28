package com.ariel.BeersApi.Repositories;

import com.ariel.BeersApi.Model.DrinkTypes;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDrinkTypesRepository extends IRepository<DrinkTypes, Integer>{

    Optional<DrinkTypes> findByDescription(String description);


}
