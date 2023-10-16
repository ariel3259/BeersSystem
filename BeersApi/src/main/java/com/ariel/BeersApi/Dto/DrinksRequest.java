package com.ariel.BeersApi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DrinksRequest {
    @NotBlank()
    private String name;
    @NotNull()
    private float alcoholRate;
    @NotNull()
    private double price;
    @NotNull()
    private int drinkTypesId;
}
