package com.example.commonContext.service;

import com.example.commonContext.entity.CommonContext;
import redis.clients.jedis.exceptions.JedisDataException;

import java.time.Duration;

public interface ContextService<V> {

    void createContext(String token, CommonContext context);

    void deleteContext(String token);

    void createContextField(String fieldName,V fieldValue, String token) throws JedisDataException;

    V getContextField(String token, String fieldName);

    void updateContextField(String fieldName,V fieldValue, String token) throws JedisDataException;

    void deleteContextField(String fieldName, V fieldValue, String token) throws JedisDataException;

    void refreshTimeToLive(String token);
}
