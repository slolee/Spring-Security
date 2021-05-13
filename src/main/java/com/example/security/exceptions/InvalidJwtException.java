package com.example.security.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String msg) {
        super(msg);
    }
}
