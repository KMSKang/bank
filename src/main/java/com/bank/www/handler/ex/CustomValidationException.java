package com.bank.www.handler.ex;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {
    private final Map<String, String> erroMap;

    public CustomValidationException(String message, Map<String, String> erroMap) {
        super(message);
        this.erroMap = erroMap;
    }
}
