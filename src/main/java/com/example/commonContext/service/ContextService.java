package com.example.commonContext.service;

import com.example.commonContext.entity.CommonContext;

import java.time.Duration;

public interface ContextService<N,V> {

    void createContextField(String token, CommonContext context);

    CommonContext getContextField(String token);

    void updateContextField(N fieldName,V fieldValue, String token);

    void deleteContextField(String token);

    void refreshTimeToLive(String token);
}
