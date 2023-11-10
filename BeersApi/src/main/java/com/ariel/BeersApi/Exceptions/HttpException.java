package com.ariel.BeersApi.Exceptions;

import lombok.Getter;

@Getter
public class HttpException extends Exception {
    private final int statusCode;

    public HttpException(int sc, String message) {
        super(message);
        statusCode = sc;
    }
}
