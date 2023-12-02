package com.example.plathome.login.domain;

public class UserContext {
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void set(String nickname) {
        userContext.set(nickname);
    }

    public static String get() {
        return userContext.get();
    }

    public static void remove() {
        userContext.remove();
    }
}