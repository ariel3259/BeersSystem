package com.ariel.BeersApi.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
@Getter
public class DrinkTypesRequest {
    @NotEmpty()
    private String description;
}
