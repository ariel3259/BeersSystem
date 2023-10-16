package com.ariel.BeersApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DrinkTypesResponse {
    private int drinkTypesId;
    private String description;
}
