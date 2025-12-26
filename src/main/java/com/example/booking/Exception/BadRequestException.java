package com.example.booking.Exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
