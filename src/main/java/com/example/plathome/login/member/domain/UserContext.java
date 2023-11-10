package com.example.plathome.login.member.domain;

public class UserContext {
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void set(String username) {
        userContext.set(username);
    }

    public static String get() {
        return userContext.get();
    }

    public static void remove() {
        userContext.remove();
    }
}