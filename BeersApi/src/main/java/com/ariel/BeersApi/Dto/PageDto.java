package com.ariel.BeersApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageDto<T>{
    List<T> elements;
    long totalItems;

}
