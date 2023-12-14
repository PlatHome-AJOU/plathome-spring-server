package com.example.plathome.global.constant;

public class JwtStaticField {
    public static final String BEARER = "Bearer ";
    public static final String REFRESH_URL = "/token";
    public static final String ACCESS_HEADER = "X-Access-Token";
    public static final String REFRESH_HEADER = "X-Refresh-Token";
    public static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 30; // Access token is 30 minutes.
    public static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24; // Refresh token is one day.
}
