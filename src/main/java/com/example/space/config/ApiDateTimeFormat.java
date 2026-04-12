package com.example.space.config;

public final class ApiDateTimeFormat {

    private ApiDateTimeFormat() {
    }

    public static final String PATTERN = "HH:mm:ss dd-MM-yyyy";
    public static final String EXAMPLE = "14:30:45 12-04-2026";
    public static final String REGEX = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d\\s(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-\\d{4}$";
}
