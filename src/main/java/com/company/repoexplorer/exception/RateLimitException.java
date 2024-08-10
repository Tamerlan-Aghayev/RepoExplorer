package com.company.repoexplorer.exception;

public class RateLimitException extends RuntimeException {
    public RateLimitException(String message) {
        super(message);
    }
}