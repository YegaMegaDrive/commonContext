package com.example.commonContext.repository;

import com.example.commonContext.entity.CommonContext;

public interface ContextRepository {
    void createContext(String token, CommonContext context);

   CommonContext getContext(String token);

   void updateContext(CommonContext context);

   void deleteContext(String token);
}
