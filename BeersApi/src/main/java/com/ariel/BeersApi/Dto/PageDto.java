package com.ariel.BeersApi.Dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PageDto<T>{
    List<T> elements;
    long totalItems;

}
