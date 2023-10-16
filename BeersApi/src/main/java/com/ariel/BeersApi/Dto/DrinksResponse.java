package com.ariel.BeersApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DrinksResponse {
    private int drinksId;
    private String name;
    private float alcoholRate;
    private double price;
    private DrinkTypesResponse drinkType;
}
