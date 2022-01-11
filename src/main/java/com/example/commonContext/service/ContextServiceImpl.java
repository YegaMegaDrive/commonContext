package com.example.commonContext.service;

import com.example.commonContext.repository.ContextRepository;
import com.example.commonContext.repository.ContextRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContextServiceImpl implements ContextService{

    private final ContextRepository repository;

    @Autowired
    public ContextServiceImpl(ContextRepositoryImpl contextRepository) {
        this.repository = contextRepository;
    }


}
