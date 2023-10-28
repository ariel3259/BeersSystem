package com.ariel.BeersApi.Repositories;

import com.ariel.BeersApi.Model.Drinks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IDrinksRepository extends IRepository<Drinks, Integer>{
    public Page<Drinks> getAllByDrinkTypesIdAndStatusTrue(Pageable pageable, int drinkTypesId);

}
