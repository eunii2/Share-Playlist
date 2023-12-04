package com.msp.membership.config.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class CustomApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    private Map<String, String> errors;

    public CustomApiException(String message) {
        this.message = message;
    }
}
