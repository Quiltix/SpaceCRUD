package com.example.space.config;

public final class ApiDateFormat {

    private ApiDateFormat() {
    }

    public static final String PATTERN = "dd-MM-yyyy";
    public static final String EXAMPLE = "12-04-2026";
    public static final String REGEX = "^(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-\\d{4}$";
}
