package com.kalita.projects.service.exceptions;

public class CityNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "City not found in Base";
    }
}
