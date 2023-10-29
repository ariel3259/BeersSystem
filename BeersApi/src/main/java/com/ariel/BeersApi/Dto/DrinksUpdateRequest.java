package com.ariel.BeersApi.Dto;

import lombok.Getter;

import java.util.Optional;

@Getter
public class DrinksUpdateRequest {
    private Optional<String> name;
    private Optional<Float> alcoholRate;
    private Optional<Double> price;
    private Optional<Integer> drinkTypeId;
}
