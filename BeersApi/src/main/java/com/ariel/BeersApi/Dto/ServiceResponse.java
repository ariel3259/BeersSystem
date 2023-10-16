package com.ariel.BeersApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceResponse<T>{
    private String message;
    private boolean error;
    private T response;
    public static <T> ServiceResponse<T> success(T response){
        return new ServiceResponse<T>("", false, response);
    }

    public static <T> ServiceResponse<T> error(String message) {
        return new ServiceResponse<T>(message, true, null);
    }
}
