package com.example.plathome.global.service;

public interface RedisService {
    void setData(String key, String value);
    String getData(String key);
    void deleteData(String key);
    Boolean checkExistValue(String value);
}
